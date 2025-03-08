package com.luannv.order.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "permissions")
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "permission_name")
  private String permissionName;
  // relations
  @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
  private List<RolePermission> rolePermissions;

}