package ru.rsu.core.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "receipt")
public class Receipt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  @Column(name = "id")
  private long id;

  @Column(name = "debt_amount")
  @JsonProperty("debtAmount")
  private int debtAmount;

  @Column(name = "dispatch_dt")
  @JsonProperty("dispatchDate")
  private LocalDate dispatchDate;

  /**
   * @author Romanov Nikita
   * 1 - не оплачено (платёж не поступал)
   * 2 - часьтчено оплачено
   * 3 - оплачена полностью
   */

  @Column(name = "receipt_status")
  @JsonProperty("receiptStatus")
  private int receiptStatus;

  @Column(name = "active_amount")
  @JsonProperty("activeAmount")
  private int activeAmount;

  @ManyToOne(targetEntity = Address.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "r_address_id")
  @JsonIgnore
  private Address address;

  @Column(name = "r_address_id", updatable = false, insertable = false)
  @JsonProperty("addressId")
  private Long refAddressId;

  public Receipt() {
    dispatchDate = LocalDate.now();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getDebtAmount() {
    return debtAmount;
  }

  public void setDebtAmount(int debtAmount) {
    this.debtAmount = debtAmount;
  }

  public LocalDate getDispatchDate() {
    return dispatchDate;
  }

  public void setDispatchDate(LocalDate dispatchDate) { this.dispatchDate = dispatchDate; }

  public int getReceiptStatus() {
    return receiptStatus;
  }

  public void setReceiptStatus(int receiptStatus) {
    this.receiptStatus = receiptStatus;
  }

  public int getActiveAmount() {
    return activeAmount;
  }

  public void setActiveAmount(int activeAmount) {
    this.activeAmount = activeAmount;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address addressOfDebtor) {
    this.address = addressOfDebtor;
  }

  public Long getRefAddressId() {
    return refAddressId;
  }

  public void setRefAddressId(Long refAddressId) {
    this.refAddressId = refAddressId;
  }

  @Override
  public String toString() {
    return "Receipt{" +
        "id=" + id +
        ", debtAmount=" + debtAmount +
        ", dispatchDate=" + dispatchDate +
        ", receiptStatus=" + receiptStatus +
        ", activeAmount=" + activeAmount +
        ", refAddressId=" + refAddressId +
        '}';
  }
}
