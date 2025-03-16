package com.luannv.order.enums;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorState {
  UNCATEGORIZED(9999, "Uncategorized error.", HttpStatus.BAD_REQUEST),
  LOGIN_FAILED(1001, "Username or password is invalid.", HttpStatus.NOT_ACCEPTABLE),
  UNAUTHENTICATED(1002, "Please login to access the resources.", HttpStatus.ACCEPTED),
  API_NOT_FOUND(1003, "Page not found.", HttpStatus.NOT_FOUND),
  FIELD_NOT_EMPTY(1004, "This field cannot be empty!", HttpStatus.BAD_REQUEST),
  EMAIL_INVALID(1005, "Email is not valid!", HttpStatus.NOT_ACCEPTABLE),
  CONFIRM_PASSWORD_INVALID(1006, "Confirm password does not match the password.", HttpStatus.NOT_ACCEPTABLE),
  USERNAME_INVALID(1007, "Username is not valid.", HttpStatus.NOT_ACCEPTABLE),
  USERNAME_USED(1008, "Username has already been used.", HttpStatus.NOT_ACCEPTABLE),
  EMAIL_USED(1008, "Email has already been used.", HttpStatus.NOT_ACCEPTABLE),
  USER_NOT_FOUND(1009, "User not found.", HttpStatus.BAD_REQUEST),
  UPLOAD_AVATAR_ERROR(1010, "Failed to upload image!", HttpStatus.BAD_REQUEST),
  USER_NOT_EDITABLE(1011, "You do not have permission to update your username.", HttpStatus.BAD_REQUEST),
  OLD_PASSWORD_INVALID(1012, "Old password is incorrect.", HttpStatus.NOT_ACCEPTABLE),
  TOKEN_INVALID(1013, "Token is either null or invalid.", HttpStatus.NOT_ACCEPTABLE),
  ROLE_FORBIDDEN(1014, "You do not have permission to access this resource!", HttpStatus.FORBIDDEN);

  private final int code;
  private final String messages;
  private final HttpStatus httpStatus;
}
