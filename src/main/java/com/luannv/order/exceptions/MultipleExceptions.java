package com.luannv.order.exceptions;

import lombok.Getter;

import java.util.Map;
@Getter
public class MultipleExceptions extends RuntimeException{
	private final Map<?, ?> errors;

	public MultipleExceptions(Map<?, ?> errors) {
		super();
		this.errors = errors;
	}
}
