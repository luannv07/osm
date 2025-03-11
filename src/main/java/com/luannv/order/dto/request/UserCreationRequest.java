package com.luannv.order.dto.request;

import com.luannv.order.validators.constraints.ConfirmPasswordConstraint;
import com.luannv.order.validators.constraints.UniqueFieldConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@ConfirmPasswordConstraint(first = "password", second = "confirmPassword")
@Data
public class UserCreationRequest {
	@NotEmpty(message = "FIELD_NOT_EMPTY")
	@Pattern(regexp = "^(?![-_\\d])[\\w-]{3,15}$", message = "USERNAME_INVALID")
	@UniqueFieldConstraint(field = "username", message = "USERNAME_USED")
	private String username;
	@Email(message = "EMAIL_INVALID")
	@NotEmpty(message = "FIELD_NOT_EMPTY")
	@UniqueFieldConstraint(field = "email", message = "EMAIL_USED")
	private String email;
	@NotEmpty(message = "FIELD_NOT_EMPTY")
	private String fullName;
	@NotEmpty(message = "FIELD_NOT_EMPTY")
	private String password;
	private String confirmPassword;
}
