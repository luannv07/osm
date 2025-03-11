package com.luannv.order.exceptions;

import com.luannv.order.enums.OrderErrorState;
import lombok.Getter;

@Getter
public class SingleException extends RuntimeException{
	private final OrderErrorState orderErrorState;

	public SingleException(OrderErrorState orderErrorState) {
		super(orderErrorState.getMessages());
		this.orderErrorState = orderErrorState;
	}
}
