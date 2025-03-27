package com.luannv.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class OrderApplication {
	public static void main(String[] args) throws NoSuchFieldException {
		SpringApplication.run(OrderApplication.class, args);
	}
}
