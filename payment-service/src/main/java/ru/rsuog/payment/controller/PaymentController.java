package ru.rsuog.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ru.rsuog.payment.model.Payment;
import ru.rsuog.payment.service.PaymentService;

@RestController
@RequestMapping("/api")
@ComponentScan("ru.romanov")
public class PaymentController {

  private final PaymentService paymentService;
  private final AmqpTemplate templateForMessage;


  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

  public PaymentController(PaymentService paymentService, AmqpTemplate templateForMessage) {
    this.paymentService = paymentService;
    this.templateForMessage = templateForMessage;

  }

  @GetMapping({"/payment"})
  public List<Payment> getAllPayments() {
    logger.info("HTTP GET /api/payment");
    return paymentService.getAllPayment();
  }

  @GetMapping({"/payment/{paymentId}"})
  public Optional<Payment> getPaymentById(@PathVariable(value = "paymentId") Long id) {
    logger.info("HTTP GET /api/payment/" + id);
    return paymentService.getPaymentById(id);
  }

  @PostMapping({"/payment"})
  public Long savePayment(@RequestBody Payment payment) throws Exception {
    Long retId = null;
    try {
      retId = paymentService.addPayment(payment).getId();
      PaymentInfo paymentInfo = new PaymentInfo(payment.getRefReceiptId(), payment.getAmount(), false);
      //sendPaymentInformationByREST(paymentInfo);
      //sendPaymentInformationByRabbitMQ(paymentInfo);
      sendPaymentInformationByRestTemplate(paymentInfo);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    logger.info("HTTP POST api/payment. Request body: " + payment.toString() + " Payment Id: " + retId);
    return retId;
  }

  @DeleteMapping({"/payment/{paymentId}"})
  public boolean deletePaymentById(@PathVariable(value = "paymentId") Long id) throws Exception {
    logger.info("HTTP DELETE /api/payment/" + id);
    try {
      Payment payment = paymentService.getPaymentById(id).get();
      PaymentInfo paymentInfo = new PaymentInfo(payment.getRefReceiptId(), payment.getAmount(), true);
      //sendPaymentInformationByREST(paymentInfo);
      //sendPaymentInformationByRabbitMQ(paymentInfo);
      sendPaymentInformationByRestTemplate(paymentInfo);

      paymentService.removePaymentById(id);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }

  private void sendPaymentInformationByREST(PaymentInfo paymentInfo) throws IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    String serviceUri = "http://localhost:8080";
    HttpPut putRequestForUpdate = new HttpPut(serviceUri + "/api/receipt/" + paymentInfo.getReceiptId());
    ObjectMapper objectMapper = new ObjectMapper();
    putRequestForUpdate.setEntity(new StringEntity(objectMapper.writeValueAsString(paymentInfo)));
    putRequestForUpdate.setHeader("Accept", "application/json");
    putRequestForUpdate.setHeader("Content-type", "application/json");

    logger.info("Send payment information to: " + serviceUri + " payment information: " + paymentInfo.toString());
    CloseableHttpResponse response = httpclient.execute(putRequestForUpdate);
  }

  private void sendPaymentInformationByRabbitMQ(PaymentInfo paymentInfo) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String message = objectMapper.writeValueAsString(paymentInfo);
    templateForMessage.convertAndSend("paymentQueue", message);

    logger.info("Send new message to RabbitMQ. Queue = paymentQueue. Message: " + message);
  }

  private void sendPaymentInformationByRestTemplate(PaymentInfo paymentInfo) throws JsonProcessingException {
    RestTemplate restTemplate = new RestTemplate();
    StringBuilder serviceUri = new StringBuilder("http://localhost:8080/api/receipt/");
    serviceUri.append(paymentInfo.getReceiptId());

    HttpEntity<PaymentInfo> requestForUpdate = new HttpEntity<>(paymentInfo);
    restTemplate.exchange(serviceUri.toString(), HttpMethod.PUT, requestForUpdate, Void.class);


  }
}
