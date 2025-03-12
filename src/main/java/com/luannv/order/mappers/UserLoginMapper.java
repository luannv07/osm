package com.luannv.order.mappers;

import com.luannv.order.dto.request.UserLoginRequest;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.models.UserEntity;
import org.springframework.stereotype.Component;

import static com.luannv.order.constants.UrlConstant.avatarPath;

@Component
public class UserLoginMapper implements GenericMapper<UserEntity, UserLoginRequest, UserResponse> {
	@Override
	public UserEntity toEntity(UserLoginRequest userLoginRequestDTO) {
		return null;
	}

	@Override
	public UserResponse toResponseDTO(UserEntity userEntity) {
		return UserResponse.builder()
						.avatar(avatarPath.formatted(userEntity.getUsername()))
						.username(userEntity.getUsername())
						.email(userEntity.getEmail())
						.roleName(userEntity.getRoleUser().getRoleName())
						.fullName(userEntity.getFullName())
						.build();
	}
}
