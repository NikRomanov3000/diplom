package ru.rsu.payment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import ru.rsu.payment.model.Payment;
import ru.rsu.payment.service.PaymentService;
import ru.rsuog.model.PaymentInfo;

@RestController
@RequestMapping("/api")
public class PaymentController {

  private final PaymentService paymentService;

  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping({ "/payment" })
  public List<Payment> getAllPayments() {
    logger.info("HTTP GET /api/payment");
    return paymentService.getAllPayment();
  }

  @GetMapping({ "/payment/{paymentId}" })
  public Optional<Payment> getPaymentById(@PathVariable(value = "paymentId") Long id) {
    logger.info("HTTP GET /api/payment/" + id);
    return paymentService.getPaymentById(id);
  }

  @PostMapping({ "/payment" })
  public Long savePayment(@RequestBody Payment payment) throws Exception {
    Long retId;
    try {
      PaymentInfo paymentInfo = new PaymentInfo(payment.getRefReceiptId(), payment.getAmount(),
                                                false);
      retId = paymentService.sendPaymentInformationByRabbitMQ(paymentInfo, payment);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    logger.info(
        "HTTP POST api/payment. Request body: " + payment.toString() + " Payment Id: " + retId);
    return retId;
  }

  @DeleteMapping({ "/payment/{paymentId}" })
  public boolean deletePaymentById(@PathVariable(value = "paymentId") Long id) throws Exception {
    logger.info("HTTP DELETE /api/payment/" + id);
    try {
      Payment payment = paymentService.getPaymentById(id).get();
      PaymentInfo paymentInfo = new PaymentInfo(payment.getRefReceiptId(), payment.getAmount(),
                                                true);
      paymentService.sendPaymentInformationByRabbitMQ(paymentInfo, payment);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }
}
