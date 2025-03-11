package com.luannv.order.validators;

import com.luannv.order.repositories.UserRepository;
import com.luannv.order.validators.constraints.UniqueFieldConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueFieldConstraintValidator implements ConstraintValidator<UniqueFieldConstraint, Object> {
	private final UserRepository userRepository;

	public UniqueFieldConstraintValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private String field;

	@Override
	public void initialize(UniqueFieldConstraint constraintAnnotation) {
		field = constraintAnnotation.field();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		if (value == null) return valid;
		if (userRepository.existsByUsername(value.toString()) || userRepository.existsByEmail(value.toString()))
			valid = false;
		if (valid==false)
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
							.addConstraintViolation();
		return valid;
	}

}
