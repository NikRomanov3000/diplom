package ru.rsu.payment.model;

import javax.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="payment")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  @Column(name = "id")
  private Long id;

  @Column(name = "amount")
  @JsonProperty("amount")
  private int amount;

  @Column(name = "payment_date")
  @JsonProperty("paymentDate")
  private LocalDate paymentDate;

  @Column(name = "r_receipt_id", updatable = false)
  @JsonProperty(required = true, value = "receiptId" )
  private Long refReceiptId;

  public Payment() {
    paymentDate = LocalDate.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }

  public Long getRefReceiptId() {
    return refReceiptId;
  }

  public void setRefReceiptId(Long refReceiptId) {
    this.refReceiptId = refReceiptId;
  }

  @Override
  public String toString() {
    return "Payment{" +
        "id=" + id +
        ", amount=" + amount +
        ", paymentDate=" + paymentDate +
        ", refReceiptId=" + refReceiptId +
        '}';
  }
}
