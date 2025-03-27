package com.luannv.order.services;

import com.luannv.order.dto.request.UserUpdateRequest;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.MultipleExceptions;
import com.luannv.order.exceptions.SingleException;
import com.luannv.order.mappers.UserMapper;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public byte[] getUserAvatar(String username) {
		UserEntity user = userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleException(OrderErrorState.USER_NOT_FOUND));
		return user.getAvatar();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAll() {
		List<UserResponse> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> users.add(userMapper.toResponseDTO(user)));
		return users;
	}

	private UserEntity getUserEntityByUsername(String username) {
		return userRepository.findByUsername(username)
						.orElseThrow(() -> new SingleException(OrderErrorState.USER_NOT_FOUND));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public UserResponse getByUsername(String username) {
		UserEntity user = getUserEntityByUsername(username);
		return userMapper.toResponseDTO(user);
	}

	@PreAuthorize("hasAnyRole('ADMIN') or #username == authentication.name")
	public UserResponse updateUser(String username, UserUpdateRequest userUpdateRequest,
																 MultipartFile multipartFile) throws IOException {
		UserEntity user = getUserEntityByUsername(username);
		Map<String, String> errs = new HashMap<>();

		if (userUpdateRequest.getFullName() != null && !userUpdateRequest.getFullName().isEmpty()
						&& !userUpdateRequest.getFullName().equals(user.getFullName()))
			user.setFullName(userUpdateRequest.getFullName());
		if (multipartFile != null && !multipartFile.isEmpty())
			user.setAvatar(multipartFile.getBytes());
		if (userUpdateRequest.getOldPassword() != null && !userUpdateRequest.getOldPassword().isEmpty()) {
			if (passwordEncoder.matches(userUpdateRequest.getOldPassword(), user.getPassword())) {
				if (userUpdateRequest.getNewPassword() == null || userUpdateRequest.getNewPassword().isEmpty())
					errs.put("newPassword", OrderErrorState.FIELD_NOT_EMPTY.getMessages());
				if (userUpdateRequest.getConfirmPassword() == null || userUpdateRequest.getConfirmPassword().isEmpty())
					errs.put("confirmPassword", OrderErrorState.FIELD_NOT_EMPTY.getMessages());
				else if (!userUpdateRequest.getConfirmPassword().equals(userUpdateRequest.getNewPassword()))
					errs.put("confirmPassword", OrderErrorState.CONFIRM_PASSWORD_INVALID.getMessages());
				else
					user.setPassword(passwordEncoder.encode(userUpdateRequest.getNewPassword()));
			} else {
				errs.put("oldPassword", OrderErrorState.OLD_PASSWORD_INVALID.getMessages());
			}
		}
		if (!errs.isEmpty())
			throw new MultipleExceptions(errs);

		return userMapper.toResponseDTO(userRepository.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public String deleteUserByUsername(String username) {
		UserEntity user = getUserEntityByUsername(username);
		userRepository.delete(user);
		return "Deleted " + username;
	}

	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getMyInfo() {
		System.out.println(">>> Run here");
		Authentication obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj == null)
			throw new SingleException(OrderErrorState.UNAUTHENTICATED);
		String name = obj.getName();
		UserEntity user = userRepository.findByUsername(name)
						.orElseThrow(() -> new SingleException(OrderErrorState.USER_NOT_FOUND));
		return userMapper.toResponseDTO(user);
	}
}
