package com.luannv.order.configurations;

import com.luannv.order.enums.PermissionsName;
import com.luannv.order.enums.RolesName;
import com.luannv.order.models.Permission;
import com.luannv.order.models.Role;
import com.luannv.order.models.RolePermission;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.PermissionRepository;
import com.luannv.order.repositories.RolePermissionRepository;
import com.luannv.order.repositories.RoleRepository;
import com.luannv.order.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StartupDataLoader implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;
	private final RolePermissionRepository rolePermissionRepository;
	private final PasswordEncoder passwordEncoder;
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASSWORD = "123";

	public StartupDataLoader(UserRepository userRepository, PermissionRepository permissionRepository,
													 RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
		this.rolePermissionRepository = rolePermissionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		setupRoles();
		setupPermissions();
		setupUsers();
		setupRolePermissions();
	}

	private void setupRoles() {
		if (roleRepository.count() == 0) {
			List<Role> roles = Stream.of(RolesName.values())
							.map(roleEnum -> Role.builder()
											.roleName(roleEnum.name())
											.build())
							.collect(Collectors.toList());
			roleRepository.saveAll(roles);
		}
	}

	private void setupPermissions() {
		if (permissionRepository.count() == 0) {
			List<Permission> permissions = Stream.of(PermissionsName.values())
							.map(permEnum -> Permission.builder()
											.permissionName(permEnum.name())
											.build())
							.collect(Collectors.toList());
			permissionRepository.saveAll(permissions);
		}
	}

	private void setupUsers() {
		if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
			userRepository.save(UserEntity.builder()
							.username(ADMIN_USERNAME)
							.password(passwordEncoder.encode(ADMIN_PASSWORD))
							.roleUser(roleRepository.findById(1).get())
							.build());
		}
	}

	private void setupRolePermissions() {
		if (rolePermissionRepository.count() > 0) return;

		Role adminRole = roleRepository.findById(1).get();
		Role userRole = roleRepository.findById(2).get();
		List<Permission> permissions = permissionRepository.findAll();

		List<RolePermission> rolePermissions = permissions.stream()
						.map(permission -> RolePermission.builder()
										.role(adminRole)
										.permission(permission)
										.build())
						.collect(Collectors.toList());

		permissions.stream()
						.filter(p -> p.getPermissionName().equals("READ"))
						.findFirst()
						.ifPresent(permission -> rolePermissions.add(RolePermission.builder()
										.role(userRole)
										.permission(permission)
										.build()));

		rolePermissionRepository.saveAll(rolePermissions);
	}
}
