package com.luannv.order.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorState {
  UNCATEGORIZED(9999, "Uncategorized error.", HttpStatus.BAD_REQUEST),
  LOGIN_FAILED(1001, "Username or password invalid.", HttpStatus.NOT_ACCEPTABLE),
  UNAUTHENTICATED(1002, "Please login to access the resources.", HttpStatus.ACCEPTED),
  API_NOT_FOUND(1003, "Page not found.", HttpStatus.NOT_FOUND),
  FIELD_NOT_EMPTY(1004, "This field can not empty!", HttpStatus.BAD_REQUEST),
  EMAIL_INVALID(1005, "Email is not valid!", HttpStatus.NOT_ACCEPTABLE),
  CONFIRM_PASSWORD_INVALID(1006,"Confirm password is not like password", HttpStatus.NOT_ACCEPTABLE),
  USERNAME_INVALID(1007, "Username is not valid.", HttpStatus.NOT_ACCEPTABLE),
  USERNAME_USED(1008, "Username has been used.", HttpStatus.NOT_ACCEPTABLE),
  EMAIL_USED(1008, "Email has been used.", HttpStatus.NOT_ACCEPTABLE),
  USER_NOT_FOUND(1009, "Username not found", HttpStatus.BAD_REQUEST),
  UPLOAD_AVATAR_ERROR(1010, "Failed to upload image!", HttpStatus.BAD_REQUEST)
  ;

  private final int code;
  private final String messages;
  private final HttpStatus httpStatus;
}