package com.luannv.order.services;

import com.luannv.order.dto.response.RoleResponse;
import com.luannv.order.mappers.RoleMapper;
import com.luannv.order.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
		this.roleRepository = roleRepository;
		this.roleMapper = roleMapper;
	}

	public List<RoleResponse> getAllRoles() {
		return roleRepository.findAll().stream().map(role -> roleMapper.toResponseDTO(role)).toList();
	}
}
