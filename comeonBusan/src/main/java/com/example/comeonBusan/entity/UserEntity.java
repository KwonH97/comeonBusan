package com.example.comeonBusan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String role;
	private String email;
	private String token; // 비밀번호 재설정을 위한 토큰
	
	public void updatePassword(String encode) {
		
		this.password = encode;
	}
}
