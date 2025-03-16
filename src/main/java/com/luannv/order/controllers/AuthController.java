package com.luannv.order.controllers;

import com.luannv.order.dto.request.TokenRequest;
import com.luannv.order.dto.request.UserCreationRequest;
import com.luannv.order.dto.request.UserLoginRequest;
import com.luannv.order.services.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	public AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> loginSystem(@RequestBody UserLoginRequest requestDTO) throws JOSEException {
		return authService.checkLogin(requestDTO);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserCreationRequest request,
																				BindingResult bindingResult,
																				@RequestParam(value = "avatar", required = false) MultipartFile multipartFile)
					throws IOException, NoSuchFieldException {
		return authService.userCreation(request, bindingResult, multipartFile);
	}
	@PostMapping("/check-valid-token")
	public ResponseEntity<?> tokenValid(@RequestBody TokenRequest tokenRequest) throws ParseException, JOSEException {
		return authService.checkValidToken(tokenRequest.getToken());
	}
}
