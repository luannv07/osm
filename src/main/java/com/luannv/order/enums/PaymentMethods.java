package com.luannv.order.enums;

import lombok.Getter;

@Getter
public enum PaymentMethods {
  COD(0, "COD"),
  PAY_NOW(1, "PAY_NOW");

  private final int code;
  private final String payNow;

  private PaymentMethods(int code, String payNow) {
    this.code = code;
    this.payNow = payNow;
  }

}
