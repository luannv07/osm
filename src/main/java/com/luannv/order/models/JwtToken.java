package com.luannv.order.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "jwt_token")
public class JwtToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserEntity userJwtToken;
	@Column(name = "token")
	private String token;
	@Column(name = "valid")
	private boolean valid; // true 1/ false 0
	@Column(name = "added_at")
	private LocalDate time;
}
