package com.luannv.order.validators.constraints;

import com.luannv.order.validators.UniqueFieldConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = {UniqueFieldConstraintValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueFieldConstraint {
	String field();
	String message();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
