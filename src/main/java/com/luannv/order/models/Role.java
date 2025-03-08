package com.luannv.order.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "role_name")
  private String roleName;
  // relations
  @OneToMany(mappedBy = "roleUser", cascade = CascadeType.ALL)
  private List<UserEntity> users;
  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private List<RolePermission> rolePermissions;

}
