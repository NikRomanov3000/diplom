package ru.rsu.payment.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import ru.rsu.payment.model.Payment;
import ru.rsuog.model.PaymentInfo;

public interface PaymentService {
  List<Payment> getAllPayment();
  Optional<Payment> getPaymentById(long id);
  Payment addPayment(Payment payment);
  void removePaymentById(long id);
  void sendPaymentInformationByRestTemplate(PaymentInfo paymentInfo) throws JsonProcessingException;
  Long sendPaymentInformationByRabbitMQ(PaymentInfo paymentInfo, Payment payment) throws JsonProcessingException;
}
