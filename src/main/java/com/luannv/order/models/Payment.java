package com.luannv.order.models;

import java.time.LocalDate;

import com.luannv.order.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "payments")

public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "pay_amout")
  private long payAmout;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "pay_status")
  private PaymentStatus paymentStatus;
  @OneToOne(mappedBy = "payment")
  private Order order;
  @Column(name = "create_at")
  private LocalDate createAt;
}
