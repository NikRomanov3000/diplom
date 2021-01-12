package ru.rsuog.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ru.rsuog.core.model.entity.Receipt;
import ru.rsuog.core.model.enums.ReceiptStatuses;
import ru.rsuog.core.repository.ReceiptRepository;
import ru.rsuog.core.service.ReceiptService;

@Service
@Transactional(readOnly = true)
public class ReceiptServiceImpl implements ReceiptService {
  private final ReceiptRepository receiptRepository;
  private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

  public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  @Override
  public List<Receipt> getAllReceipt() {
    return receiptRepository.findAll();
  }

  @Override
  public Optional<Receipt> getReceiptById(long id) {
    return receiptRepository.findById(id);
  }

  @Override
  @Transactional
  public Receipt addReceipt(Receipt receipt) {
    return receiptRepository.save(receipt);
  }

  @Override
  @Transactional
  public void removeReceiptById(long id) {
    receiptRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void updateReceipt(PaymentInfo paymentInfo) throws Exception {
    Optional<Receipt> receiptOptional = receiptRepository.findById(paymentInfo.getReceiptId());
    Receipt receiptForUpdate;
    try {
      if (receiptOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                          "Receipt with id: " + paymentInfo.getReceiptId()
                                              + " doesn't exist");
      } else {
        receiptForUpdate = receiptOptional.get();
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }

    if (!paymentInfo.isDeleted()) {
      if (paymentInfo.getAmount() > 0) {
        if (receiptForUpdate.getActiveAmount() > 0) {
          receiptForUpdate.setActiveAmount(
              receiptForUpdate.getActiveAmount() - paymentInfo.getAmount());
        }
      }
    } else {
      receiptForUpdate.setActiveAmount(
          receiptForUpdate.getActiveAmount() + paymentInfo.getAmount());
    }

    checkAndUpdateReceipt(receiptForUpdate);
    logger.info("Receipt with id: " + receiptForUpdate.getId() + "was updated. New data: "
                    + receiptForUpdate.toString());
    addReceipt(receiptForUpdate);
  }

  @Override
  public void checkAndUpdateReceipt(Receipt receipt) {
    if (receipt.getActiveAmount() < receipt.getDebtAmount() && receipt.getActiveAmount() > 0) {
      receipt.setReceiptStatus(ReceiptStatuses.partPaid.getReceiptStatusValue());
    } else if (receipt.getActiveAmount() <= 0) {
      receipt.setReceiptStatus(ReceiptStatuses.fullPaid.getReceiptStatusValue());
    } else if (receipt.getActiveAmount() >= receipt.getDebtAmount()) {
      receipt.setReceiptStatus(ReceiptStatuses.notPaid.getReceiptStatusValue());
    }
  }
}
