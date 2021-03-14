package ru.rsu.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import ru.rsu.core.model.entity.Receipt;
import ru.rsu.core.service.AddressService;
import ru.rsu.core.service.ReceiptService;
import ru.rsuog.model.PaymentInfo;

@RestController
@RequestMapping("/api")
public class ReceiptController {
  private final ReceiptService receiptService;
  private final AddressService addressService;
  private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

  public ReceiptController(ReceiptService receiptService, AddressService addressService) {
    this.receiptService = receiptService;
    this.addressService = addressService;
  }

  @GetMapping({"/receipt"})
  public List<Receipt> getAllReceipt() {
    logger.info("HTTP Query: GET /api/receipt");
    return receiptService.getAllReceipt();
  }

  @GetMapping({"/receipt/{receiptId}"})
  public Optional<Receipt> getReceiptById(@PathVariable(value = "receiptId") Long id) {
    logger.info("HTTP Query: GET /api/receipt/"+id);
    return receiptService.getReceiptById(id);
  }

  @PostMapping({"/receipt"})
  public Long saveReceipt(@RequestBody Receipt receipt) throws Exception {
    Long retId = null;
    try {
      if(addressService.getAddressById(receipt.getRefAddressId()).isEmpty()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address with id: "+receipt.getRefAddressId()+" doesn't exist");
      } else {
        receipt.setAddress(addressService.getAddressById(receipt.getRefAddressId()).get());
        retId = receiptService.addReceipt(receipt).getId();
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    logger.info("HTTP Query: POST /api/receipt request body: " + receipt.toString() + " Receipt Id: " +retId);
    return retId;
  }

  @DeleteMapping({"/receipt/{receiptId}"})
  public boolean deleteReceiptById(@PathVariable(value = "receiptId") Long id) throws Exception {
    logger.info("HTTP Query: DELETE /api/receipt/" + id);
    try {
      receiptService.removeReceiptById(id);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }

  @PutMapping({"/receipt/{receiptId}"})
  public boolean updateReceiptAfterReceivingPayment(@PathVariable(value = "receiptId") Long id, @RequestBody
      PaymentInfo paymentInfo) throws Exception {
    logger.info("HTTP Query: PUT /api/receipt/" + id + " Request body: " + paymentInfo.toString());
    try {
      receiptService.updateReceipt(paymentInfo);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw new Exception(ex);
    }
    return true;
  }
}
