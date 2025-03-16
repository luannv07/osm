package com.luannv.order.repositories;

import com.luannv.order.models.JwtToken;
import com.luannv.order.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
}
