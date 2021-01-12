package ru.rsuog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInfo {

  @JsonProperty("receiptId")
  private Long receiptId;

  @JsonProperty("amount")
  private int amount;

  @JsonProperty("isDeleted")
  private boolean isDeleted;

  public PaymentInfo() {
  }

  public PaymentInfo(Long receiptId, int amount, boolean isDeleted) {
    this.receiptId = receiptId;
    this.amount = amount;
    this.isDeleted = isDeleted;
  }

  public Long getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(Long receiptId) {
    this.receiptId = receiptId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  @Override
  public String toString() {
    return "PaymentInfo{" +
        "receiptId=" + receiptId +
        ", amount=" + amount +
        ", isDeleted=" + isDeleted +
        '}';
  }
}
