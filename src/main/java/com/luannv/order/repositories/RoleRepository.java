package com.luannv.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luannv.order.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
