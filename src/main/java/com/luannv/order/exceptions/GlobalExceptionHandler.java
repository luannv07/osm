package com.luannv.order.exceptions;

import com.luannv.order.dto.response.ApiResponse;
import com.luannv.order.enums.OrderErrorState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = SingleException.class)
	public ResponseEntity<ApiResponse<?, ?>> singleExpHandle(SingleException se) {
		OrderErrorState orderErrorState = se.getOrderErrorState();
		return ResponseEntity.status(orderErrorState.getHttpStatus()).body(ApiResponse.<String, String>builder()
						.code(orderErrorState.getCode())
						.message(orderErrorState.getMessages())
						.build());
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<?, ?>> apiNotFound(HttpRequestMethodNotSupportedException hrmnse) {
		OrderErrorState orderErrorState = OrderErrorState.API_NOT_FOUND;
		return ResponseEntity.status(orderErrorState.getHttpStatus()).body(ApiResponse.builder()
						.message(orderErrorState.getMessages())
						.code(orderErrorState.getCode())
						.build());
	}

	@ExceptionHandler(value = MultipleExceptions.class)
	public ResponseEntity<ApiResponse<?, ?>> multipleExpHandle(MultipleExceptions me) {
		return ResponseEntity.badRequest().body(ApiResponse.builder()
						.result(me.getErrors())
						.code(-1)
						.build());
	}
}
