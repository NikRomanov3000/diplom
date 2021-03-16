package ru.rsu.payment.service;

import java.util.List;
import java.util.Optional;

import ru.rsu.payment.model.Payment;

public interface PaymentService {
  List<Payment> getAllPayment();

  Optional<Payment> getPaymentById(long id);

  Payment addPayment(Payment payment);

  void removePaymentById(long id);
}
