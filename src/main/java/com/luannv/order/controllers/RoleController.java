package com.luannv.order.controllers;

import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.dto.response.RoleResponse;
import com.luannv.order.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public ResponseEntity<?> getAllPermissions() {
		return ResponseEntity.ok().body(ApiResponse.<String, List<RoleResponse>>builder()
										.result(roleService.getAllRoles())
										.code(HttpStatus.OK.value())
										.message("Get success!")
						.build());
	}
}
