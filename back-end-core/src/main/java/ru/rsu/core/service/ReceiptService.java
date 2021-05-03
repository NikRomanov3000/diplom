package ru.rsu.core.service;

import java.util.List;
import java.util.Optional;

import ru.rsu.core.model.entity.Receipt;
import ru.rsuog.model.PaymentInfo;

public interface ReceiptService {
  List<Receipt> getAllReceipt();

  Optional<Receipt> getReceiptById(long id);

  Receipt addReceipt(Receipt receipt);

  void removeReceiptById(long id);

  PaymentInfo updateReceipt(PaymentInfo paymentInfo) throws Exception;

  void checkAndUpdateReceipt(Receipt receipt);

}
