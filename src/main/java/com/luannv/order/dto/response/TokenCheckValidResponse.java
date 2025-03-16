package com.luannv.order.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class TokenCheckValidResponse {
	private int code;
	private boolean result;
	private long timestamp;
}
