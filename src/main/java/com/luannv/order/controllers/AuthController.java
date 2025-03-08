package com.luannv.order.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luannv.order.dto.request.UserLoginRequestDTO;
import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  public AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<?, ?>> loginSystem(@RequestBody UserLoginRequestDTO requestDTO) {
    ApiResponse<?, ?> apiResponse = this.authService.checkLogin(requestDTO);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
  }

}
