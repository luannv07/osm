package com.luannv.order.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
	private String fullName;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
