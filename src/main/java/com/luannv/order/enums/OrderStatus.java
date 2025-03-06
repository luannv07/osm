package com.luannv.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING(0, "pending"),
  PAID(1, "paid"),
  SHIPPED(2, "shipped"),
  CANCELLED(3, "cancelled");

  private final int code;
  private final String description;

  private OrderStatus(int code, String description) {
    this.code = code;
    this.description = description;
  }

}
