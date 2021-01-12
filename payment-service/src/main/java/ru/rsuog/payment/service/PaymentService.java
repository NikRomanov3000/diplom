package ru.rsuog.payment.service;

import java.util.List;
import java.util.Optional;

import ru.rsuog.payment.model.Payment;

public interface PaymentService {
  List<Payment> getAllPayment();

  Optional<Payment> getPaymentById(long id);

  Payment addPayment(Payment payment);

  void removePaymentById(long id);
}
