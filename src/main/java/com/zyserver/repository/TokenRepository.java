package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zyserver.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity,Integer>,JpaSpecificationExecutor<TokenEntity> {
	TokenEntity findByToken(String token);
}
