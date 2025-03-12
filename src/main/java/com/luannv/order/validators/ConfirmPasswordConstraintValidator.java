package com.luannv.order.validators;

import com.luannv.order.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class ConfirmPasswordConstraintValidator implements ConstraintValidator<ConfirmPasswordConstraint, Object> {
	private String firstField;
	private String secondField;

	@Override
	public void initialize(ConfirmPasswordConstraint constraintAnnotation) {
		firstField = constraintAnnotation.first();
		secondField = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
// cach 1: cách này chạy nhanh hơn và ko phụ thuộc vào spring
//			Field first = value.getClass().getDeclaredField(firstField);
//			Field second = value.getClass().getDeclaredField(secondField);
//			first.setAccessible(true);
//			second.setAccessible(true);
//
//			Object fValue = first.get(value);
//			Object sValue = second.get(value);
			System.out.println("Loi da vao day");
			Object fValue = BeanUtils.getPropertyDescriptor(value.getClass(), firstField).getReadMethod().invoke(value);
			Object sValue = BeanUtils.getPropertyDescriptor(value.getClass(), secondField).getReadMethod().invoke(value);
			if (fValue == null || sValue == null || !fValue.equals(sValue)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
								.addPropertyNode(secondField)
								.addConstraintViolation();
				return false;
			}
			return true;
		} catch (IllegalAccessException | InvocationTargetException e) {
			return false;
		}
	}
}