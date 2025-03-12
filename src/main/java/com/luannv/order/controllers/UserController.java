package com.luannv.order.controllers;

import com.luannv.order.dto.request.UserUpdateRequest;
import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/{username}/avatar", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public ResponseEntity<?> getUserAvatar(@PathVariable String username) {
		byte[] userAvatar = userService.getUserAvatar(username);
		return ResponseEntity.ok().body(userAvatar);
	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAll());
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		UserResponse userResponse = userService.getByUsername(username);
		return ResponseEntity.ok().body(userResponse);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> editUser(@PathVariable String username
					, @ModelAttribute UserUpdateRequest userUpdateRequest
					, @RequestParam(value = "avatar", required = false) MultipartFile multipartFile) throws IOException {
		UserResponse userResponse = userService.updateUser(username, userUpdateRequest, multipartFile);
		return ResponseEntity.ok().body(userResponse);
	}
	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username) {
		return ResponseEntity.ok().body(ApiResponse.builder()
										.code(HttpStatus.OK.value())
										.message(userService.deleteUserByUsername(username))
										.result(System.currentTimeMillis())
						.build());
	}
}
