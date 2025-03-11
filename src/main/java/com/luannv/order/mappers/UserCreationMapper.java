package com.luannv.order.mappers;

import com.luannv.order.dto.request.UserCreationRequest;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.SingleException;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.luannv.order.constants.ConstantUrl.avatarPath;

@Component
public class UserCreationMapper implements GenericMapper<UserEntity, UserCreationRequest, UserResponse> {
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserCreationMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserEntity toEntity(UserCreationRequest request) {
		return UserEntity.builder()
						.createAt(LocalDate.now())
						.roleUser(roleRepository.findById(2).orElseThrow(() -> new SingleException(OrderErrorState.UNCATEGORIZED)))
						.password(passwordEncoder.encode(request.getPassword()))
						.email(request.getEmail())
						.fullName(request.getFullName())
						.username(request.getUsername())
						.build();
	}

	@Override
	public UserResponse toResponseDTO(UserEntity userEntity) {
		return UserResponse.builder()
						.username(userEntity.getUsername())
						.roleName(userEntity.getRoleUser().getRoleName())
						.avatar(avatarPath.formatted(userEntity.getUsername()))
						.email(userEntity.getEmail())
						.fullName(userEntity.getFullName())
						.build();
	}
}
