package com.luannv.order.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.UTF8Reader;
import com.luannv.order.enums.OrderErrorState;
import com.luannv.order.exceptions.SingleException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Value("${jwt.secretKey}")
	private String secretKey;
	private static String[] PUBLIC_ENDPOINT = {
					"/api/auth/**"
	};
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
						.csrf(csrf -> csrf.disable())
						.authorizeHttpRequests(auth -> auth
										.requestMatchers(PUBLIC_ENDPOINT).permitAll()
										.anyRequest().authenticated()
						);
		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwtConfigurer -> jwtConfigurer.decoder(decoder())
										.jwtAuthenticationConverter(roleConverter())
						));
		httpSecurity.exceptionHandling(exp ->
						exp.accessDeniedHandler((request, response, accessDeniedException) -> {
											handleAccess(response, HttpServletResponse.SC_FORBIDDEN, OrderErrorState.ROLE_FORBIDDEN);
										})
										.authenticationEntryPoint((request, response, authException) -> {
											handleAccess(response, HttpServletResponse.SC_UNAUTHORIZED, OrderErrorState.UNAUTHENTICATED);
										})
		);
		return httpSecurity.build();
	}

	private JwtAuthenticationConverter roleConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtDecoder decoder() {
		SecretKeySpec spec = new SecretKeySpec(secretKey.getBytes(), "HS512");
		return NimbusJwtDecoder.withSecretKey(spec)
						.macAlgorithm(MacAlgorithm.HS512)
						.build();
	}

	private static void handleAccess(HttpServletResponse response, int status, OrderErrorState orderErrorState) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> hashResponse = new HashMap<>();

		hashResponse.put("code", String.valueOf(orderErrorState.getCode()));
		hashResponse.put("messages:", orderErrorState.getMessages());

		String jsonResponse = new ObjectMapper().writeValueAsString(hashResponse);

		response.getWriter().write(jsonResponse);
	}
}
