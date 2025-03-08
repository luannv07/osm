package com.luannv.order.services;

import org.springframework.stereotype.Service;

import com.luannv.order.dto.request.UserLoginRequestDTO;
import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.enums.OrderErrorState;

@Service
public class AuthService {
  private final String username = "admin";
  private final String password = "123";

  // public ApiResponse : check login
  public ApiResponse<?, ?> checkLogin(UserLoginRequestDTO requestDTO) {
    if (password.equals(requestDTO.getPassword()) && username.equals(requestDTO.getUsername())) {
      return ApiResponse.builder()
          .code(200)
          .message("Login success!")
          .result(requestDTO)
          .build();
    }
    return ApiResponse.builder()
        .code(OrderErrorState.LOGIN_FAILED.getCode())
        .message(OrderErrorState.LOGIN_FAILED.getMessages())
        .result(null)
        .build();
  }
}
