package com.luannv.order.services;

import com.luannv.order.dto.request.UserCreationRequest;
import com.luannv.order.dto.request.UserLoginRequest;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.MultipleExceptions;
import com.luannv.order.exceptions.SingleException;
import com.luannv.order.mappers.UserCreationMapper;
import com.luannv.order.mappers.UserLoginMapper;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.UserRepository;
import com.luannv.order.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.luannv.order.constants.ConstantUrl.defaultAvatarPath;

@Slf4j
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserLoginMapper userLoginMapper;
	private final UserCreationMapper userCreationMapper;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
										 UserLoginMapper userLoginMapper, UserCreationMapper userCreationMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userLoginMapper = userLoginMapper;
		this.userCreationMapper = userCreationMapper;
	}

	public ResponseEntity<?> checkLogin(UserLoginRequest requestDTO) {
		UserEntity userEntity = userRepository.findByUsername(requestDTO.getUsername())
						.orElseThrow(() -> new SingleException(OrderErrorState.LOGIN_FAILED));
		if (!passwordEncoder.matches(requestDTO.getPassword(), userEntity.getPassword()))
			throw new SingleException(OrderErrorState.LOGIN_FAILED);
		UserResponse userResponseDTO = userLoginMapper.toResponseDTO(userEntity);
		return ResponseEntity.ok().body(userResponseDTO);
	}

	public ResponseEntity<?> userCreation(UserCreationRequest request,
																					BindingResult bindingResult,
																					MultipartFile multipartFile) throws IOException {
		Map<String, String> errorsResponse = new HashMap<>();
		bindingResult.getFieldErrors().forEach(err ->
						errorsResponse.put(err.getField(), OrderErrorState.valueOf(err.getDefaultMessage()).getMessages()));
		byte[] avatar = null;
		if (multipartFile == null || multipartFile.isEmpty())
			avatar = ImageUtils.getImageBytes(defaultAvatarPath);
		else
			avatar = multipartFile.getBytes();
		if (errorsResponse.isEmpty()){
			UserEntity user = userCreationMapper.toEntity(request);
			user.setAvatar(avatar);
			userRepository.save(user);
			UserResponse userResponse = userCreationMapper.toResponseDTO(user);
			return ResponseEntity.ok().body(userResponse);
		}
		throw new MultipleExceptions(errorsResponse);
	}
}
