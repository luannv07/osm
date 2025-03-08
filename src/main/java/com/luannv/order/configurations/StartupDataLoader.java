package com.luannv.order.configurations;

import com.luannv.order.enums.PermissionsName;
import com.luannv.order.enums.RolesName;
import com.luannv.order.models.Permission;
import com.luannv.order.models.Role;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.PermissionRepository;
import com.luannv.order.repositories.RoleRepository;
import com.luannv.order.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class StartupDataLoader implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;

	private final String adminUsername = "admin";
	private final String adminPassword = "123";

	public StartupDataLoader(UserRepository userRepository, PermissionRepository permissionRepository,
													 RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		setupRole();
		setupPermission();
		setupUser();
	}

	public void setupUser() {
		if (userRepository.findByUsername(adminUsername).isPresent())
			return;
		this.userRepository.save(UserEntity.builder()
						.username(adminUsername)
						.password(adminPassword)
						.roleUser(roleRepository.findById(1).get())
						.build());
	}

	public void setupPermission() {
		if (!permissionRepository.findAll().isEmpty())
			return;
		Stream.of(PermissionsName.values()).forEach(permissionsName -> {
			permissionRepository.save(Permission.builder()
							.permissionName(permissionsName.name())
							.build());
		});
	}

	public void setupRole() {
		if (!roleRepository.findAll().isEmpty())
			return;
		Stream.of(RolesName.values()).forEach(roleEnumName -> {
			roleRepository.save(Role.builder()
							.roleName(roleEnumName.name())
							.build());
		});
	}

}
