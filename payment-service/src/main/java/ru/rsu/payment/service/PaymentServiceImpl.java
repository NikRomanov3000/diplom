package ru.rsu.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.rsu.payment.controller.PaymentController;
import ru.rsu.payment.model.Payment;
import ru.rsu.payment.repository.PaymentRepository;
import ru.rsuog.model.PaymentInfo;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final AmqpTemplate templateForMessage;

  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

  public PaymentServiceImpl(PaymentRepository paymentRepository,
      AmqpTemplate templateForMessage) {
    this.paymentRepository = paymentRepository;
    this.templateForMessage = templateForMessage;
  }

  @Override
  public List<Payment> getAllPayment() {
    return paymentRepository.findAll();
  }

  @Override
  public Optional<Payment> getPaymentById(long id) {
    return paymentRepository.findById(id);
  }

  @Override
  @Transactional
  public Payment addPayment(Payment payment) {
    return paymentRepository.save(payment);
  }

  @Override
  @Transactional
  public void removePaymentById(long id) {
    paymentRepository.deleteById(id);
  }

  public void sendPaymentInformationByRabbitMQ(PaymentInfo paymentInfo) throws
      JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String message = objectMapper.writeValueAsString(paymentInfo);
    templateForMessage.convertAndSend("paymentQueue", message);

    logger.info("Send new message to RabbitMQ. Queue = paymentQueue. Message: " + message);
  }

  public void sendPaymentInformationByRestTemplate(PaymentInfo paymentInfo)
      throws JsonProcessingException {
    RestTemplate restTemplate = new RestTemplate();
    StringBuilder serviceUri = new StringBuilder("http://localhost:8080/api/receipt/");
    serviceUri.append(paymentInfo.getReceiptId());

    HttpEntity<PaymentInfo> requestForUpdate = new HttpEntity<>(paymentInfo);
    restTemplate.exchange(serviceUri.toString(), HttpMethod.PUT, requestForUpdate, Void.class);
  }
}
