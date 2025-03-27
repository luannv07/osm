package com.luannv.order.mappers;

import com.luannv.order.dto.request.RoleRequest;
import com.luannv.order.dto.response.RoleResponse;
import com.luannv.order.models.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements GenericMapper<Role, RoleRequest, RoleResponse> {
	@Override
	public Role toEntity(RoleRequest roleRequest) {
		return null;
	}

	@Override
	public RoleResponse toResponseDTO(Role role) {
		return RoleResponse.builder()
						.rolePermissions(role.getRolePermissions())
						.roleName(role.getRoleName())
						.build();
	}
}
