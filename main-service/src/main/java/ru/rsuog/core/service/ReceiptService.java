package ru.rsuog.core.service;

import java.util.List;
import java.util.Optional;

import ru.rsuog.core.model.entity.Receipt;

public interface ReceiptService {
  List<Receipt> getAllReceipt();

  Optional<Receipt> getReceiptById(long id);

  Receipt addReceipt(Receipt receipt);

  void removeReceiptById(long id);

  void updateReceipt(PaymentInfo paymentInfo) throws Exception;

  void checkAndUpdateReceipt(Receipt receipt);

}
