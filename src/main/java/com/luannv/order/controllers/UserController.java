package com.luannv.order.controllers;

import com.luannv.order.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/{username}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<?> getUserAvatar(@PathVariable String username) {
		byte[] userAvatar = userService.getUserAvatar(username);
		return ResponseEntity.ok().body(userAvatar);
	}
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAll());
	}
}
