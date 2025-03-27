package com.luannv.order.controllers;

import java.util.List;

import com.luannv.order.dto.request.TokenRequest;
import com.luannv.order.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luannv.order.dto.request.PermissionRequest;
import com.luannv.order.dto.response.PermissionResponse;
import com.luannv.order.models.Permission;
import com.luannv.order.repositories.PermissionRepository;
import com.luannv.order.services.PermissionService;



@RestController
@RequestMapping("/api/permissions")

public class PermissionController {

	private final PermissionRepository permissionRepository;

	private final PermissionService permissionService;

	public PermissionController(PermissionService permissionService, PermissionRepository permissionRepository) {
		this.permissionService = permissionService;
		this.permissionRepository = permissionRepository;
	}

	@GetMapping
	public ResponseEntity<?> getAllPermissions() {
		List<Permission> lists = permissionService.findAllPermissions();
		return ResponseEntity.ok().body(lists);
	}

	@PostMapping
	public ResponseEntity<?> addPermissions(@RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.<String, PermissionResponse>builder()
						.result(permissionService.addAnPermission(permissionRequest))
						.message("Success!")
						.code(HttpStatus.OK.value())
						.build());
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<?> deletePermission(@PathVariable String name) {
		return ResponseEntity.ok().body(ApiResponse.builder()
						.message("Deleted permission name: " + name)
						.result(permissionService.deletePermissionByName(name))
						.code(200)
						.build());
	}

	@PutMapping("/{name}")
	public ResponseEntity<?> editPermission(@PathVariable String name, @RequestBody PermissionRequest permissionRequest) {
		return ResponseEntity.ok().body(ApiResponse.builder()
						.message("Edited permission name: " + name)
						.result(permissionService.editPermissionName(name, permissionRequest))
						.code(200)
						.build());
	}
}
