package com.luannv.order.services;

import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.SingleException;
import com.luannv.order.mappers.UserMapper;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public byte[] getUserAvatar(String username) {
		UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
						new SingleException(OrderErrorState.USER_NOT_FOUND));
		return user.getAvatar();
	}

	public List<UserResponse> getAll() {
		List<UserResponse> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> users.add(userMapper.toResponseDTO(user)));
		return users;
	}
}
