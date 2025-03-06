package com.luannv.order.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  PENDING(0, "pending"),
  PAID(1, "paid"),
  FAILED(2, "failed");

  private final int code;
  private final String description;

  private PaymentStatus(int code, String description) {
    this.code = code;
    this.description = description;
  }

}
