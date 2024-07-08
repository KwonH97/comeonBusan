package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
	
}
