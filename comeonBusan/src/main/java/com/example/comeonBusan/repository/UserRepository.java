package com.example.comeonBusan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	UserEntity findByUsername(String username);
	
	Optional<UserEntity> findByEmail(String email);
	
}
