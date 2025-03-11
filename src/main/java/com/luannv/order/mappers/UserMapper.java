package com.luannv.order.mappers;

import com.luannv.order.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends UserCreationMapper {
	public UserMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super(roleRepository, passwordEncoder);
	}
}
