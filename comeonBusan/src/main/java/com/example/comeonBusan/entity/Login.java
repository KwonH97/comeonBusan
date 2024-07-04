package com.example.comeonBusan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Login {

	@Id
	private String username;
	private String password;
	private String role;
}
