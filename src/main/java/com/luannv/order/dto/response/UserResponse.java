package com.luannv.order.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String fullName;
	private String email;
	private String username;
	private String roleName;
	private String avatar;
}
