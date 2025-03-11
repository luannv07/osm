package com.luannv.order.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "full_name")
  private String fullName;

  @Lob
  @Column(name = "avatar", columnDefinition = "LONGBLOB")
  private byte[] avatar;

  @Column(name = "created_at")
  private LocalDate createAt;

  // Relations
  @ManyToOne
  @JoinColumn(name = "role_user_id")
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
