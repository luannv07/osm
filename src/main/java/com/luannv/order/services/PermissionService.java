package com.luannv.order.services;

import java.util.List;

import com.luannv.order.dto.request.TokenRequest;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.SingleException;
import org.springframework.stereotype.Service;

import com.luannv.order.dto.request.PermissionRequest;
import com.luannv.order.dto.response.PermissionResponse;
import com.luannv.order.mappers.PermissionMapper;
import com.luannv.order.models.Permission;
import com.luannv.order.repositories.PermissionRepository;

@Service
public class PermissionService {

	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
	}

	public List<Permission> findAllPermissions() {
		return permissionRepository.findAll();
	}

	public PermissionResponse addAnPermission(PermissionRequest permissionRequest) {
		if (permissionRequest.getPermission() == null || permissionRequest.getPermission().isEmpty())
			throw new SingleException(OrderErrorState.FIELD_NOT_EMPTY);
		String permissionName = permissionRequest.getPermission().toUpperCase();
		if (permissionRepository.existsByPermissionName(permissionName))
			throw new SingleException(OrderErrorState.PERMISSION_EXISTED);
		Permission permission = permissionRepository.save(permissionMapper.toEntity(permissionRequest));
		return permissionMapper.toResponseDTO(permission);
	}

	public Boolean deletePermissionByName(String name) {
		Permission permission = permissionRepository.findByPermissionName(name);
		if (permission==null)
			throw new SingleException(OrderErrorState.PERMISSION_NOTFOUND);
		permissionRepository.delete(permission);
		return true;
	}

	public PermissionResponse editPermissionName(String name, PermissionRequest permissionRequest) {
		Permission permission = permissionRepository.findByPermissionName(name);
		if (permission==null)
			throw new SingleException(OrderErrorState.PERMISSION_NOTFOUND);
		if (permissionRepository.existsByPermissionName(permissionRequest.getPermission().toUpperCase()))
			throw new SingleException(OrderErrorState.PERMISSION_EXISTED);
		permission.setPermissionName(permissionRequest.getPermission().toUpperCase());
		permissionRepository.save(permission);
		return permissionMapper.toResponseDTO(permission);
	}
}