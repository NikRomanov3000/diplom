package ru.rsuog.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.rsuog.core.model.entity.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
  List<Receipt> findAllByAddress_FullAddress(String fullAddress);
}
