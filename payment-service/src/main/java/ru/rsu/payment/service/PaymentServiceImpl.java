package ru.rsu.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import ru.rsu.payment.model.Payment;
import ru.rsu.payment.repository.PaymentRepository;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  public PaymentServiceImpl(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
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
}
