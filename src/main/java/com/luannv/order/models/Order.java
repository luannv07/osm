package com.luannv.order.models;

import java.time.LocalDate;

import com.luannv.order.enums.OrderStatus;
import com.luannv.order.enums.PaymentMethods;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "orders")

public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "total_price")
  private long totalPrice;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "order_status")
  private OrderStatus orderStatus;
  @Column(name = "create_at")
  private LocalDate createAt;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "payment_method")
  private PaymentMethods paymentMethods;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userOrder;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "payment_method_type")
  private Payment payment;
}
