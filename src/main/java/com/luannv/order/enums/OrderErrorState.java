package com.luannv.order.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorState {
  LOGIN_FAILED(1001, "Username or password invalid.", HttpStatus.NOT_ACCEPTABLE);

  private final int code;
  private final String messages;
  private final HttpStatus httpStatus;
}