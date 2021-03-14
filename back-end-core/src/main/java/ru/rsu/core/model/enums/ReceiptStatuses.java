package ru.rsu.core.model.enums;

public enum ReceiptStatuses {
  notPaid(1),
  partPaid(2),
  fullPaid(3);
  private int value;

  ReceiptStatuses(int value) {
    this.value = value;
  }

  public int getReceiptStatusValue() {
    return value;
  }
}
