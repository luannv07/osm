package com.luannv.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luannv.order.models.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
