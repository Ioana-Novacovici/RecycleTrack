package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    List<TokenEntity> findAllByUserIdAndExpiredFalse(Integer user_id);

    Optional<TokenEntity> findByToken(String token);
}
