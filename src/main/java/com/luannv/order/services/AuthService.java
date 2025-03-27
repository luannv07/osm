package com.luannv.order.services;

import com.luannv.order.configurations.SecurityConfig;
import com.luannv.order.dto.request.UserCreationRequest;
import com.luannv.order.dto.request.UserLoginRequest;
import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.dto.response.TokenCheckValidResponse;
import com.luannv.order.dto.response.UserResponse;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.MultipleExceptions;
import com.luannv.order.exceptions.SingleException;
import com.luannv.order.mappers.UserCreationMapper;
import com.luannv.order.mappers.UserLoginMapper;
import com.luannv.order.models.UserEntity;
import com.luannv.order.repositories.UserRepository;
import com.luannv.order.utils.ImageUtils;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.luannv.order.constants.UrlConstant.defaultAvatarPath;
import static com.luannv.order.utils.JwtUtils.generateToken;
import static com.luannv.order.utils.JwtUtils.isValidToken;

@Slf4j
@Service
public class AuthService {
	@Value("${jwt.secretKey}")
	private String secretKey;
	private final SecurityConfig securityConfig;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserLoginMapper userLoginMapper;
	private final UserCreationMapper userCreationMapper;

	public AuthService(SecurityConfig securityConfig, UserRepository userRepository, PasswordEncoder passwordEncoder,
										 UserLoginMapper userLoginMapper, UserCreationMapper userCreationMapper) {
		this.securityConfig = securityConfig;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userLoginMapper = userLoginMapper;
		this.userCreationMapper = userCreationMapper;
	}

	public ResponseEntity<?> checkLogin(UserLoginRequest requestDTO) throws JOSEException {
		UserEntity userEntity = userRepository.findByUsername(requestDTO.getUsername())
						.orElseThrow(() -> new SingleException(OrderErrorState.LOGIN_FAILED));
		if (requestDTO.getUsername() == null || requestDTO.getPassword() == null || !passwordEncoder.matches(requestDTO.getPassword(), userEntity.getPassword()))
			throw new SingleException(OrderErrorState.LOGIN_FAILED);
		UserResponse userResponseDTO = userLoginMapper.toResponseDTO(userEntity);
		String token = generateToken(userEntity, secretKey);
		return ResponseEntity.ok().body(ApiResponse.builder()
						.result(token)
						.code(HttpStatus.OK.value())
						.build());
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
		if (errorsResponse.isEmpty()) {
			UserEntity user = userCreationMapper.toEntity(request);
			user.setAvatar(avatar);
			userRepository.save(user);
			UserResponse userResponse = userCreationMapper.toResponseDTO(user);
			return ResponseEntity.ok().body(ApiResponse.builder()
							.code(HttpStatus.OK.value())
							.result(userResponse)
							.message(System.currentTimeMillis())
							.build());
		}
		throw new MultipleExceptions(errorsResponse);
	}

	public ResponseEntity<?> checkValidToken(String token) throws ParseException, JOSEException {
		if (token == null || !isValidToken(token, secretKey))
			throw new SingleException(OrderErrorState.TOKEN_INVALID);
		return ResponseEntity.ok().body(TokenCheckValidResponse.builder()
						.code(200)
						.result(true)
						.timestamp(System.currentTimeMillis())
						.build());
	}

}
