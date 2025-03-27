package com.luannv.order.dto.response;

import com.luannv.order.models.RolePermission;
import jakarta.annotation.security.DenyAll;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
	private String roleName;
	private List<RolePermission> rolePermissions;
}
