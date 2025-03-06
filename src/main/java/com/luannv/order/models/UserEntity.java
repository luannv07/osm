package com.luannv.order.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "full_name")
  private String fullName;

  @Lob
  @Column(name = "avatar")
  private byte[] avatar;

  @Column(name = "created_at", nullable = false)
  private LocalDate createAt;

  // Relations
  @ManyToOne
  @JoinColumn(name = "role_user_id", nullable = false)
  private Role roleUser;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserPermission> userPermissions;

  @OneToMany(mappedBy = "userReview", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Order> orders;

  @OneToOne(mappedBy = "userCart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Cart cart;
}
