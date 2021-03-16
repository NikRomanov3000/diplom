package ru.rsu.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.rsu.payment.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}