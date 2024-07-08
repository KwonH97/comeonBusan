package com.example.comeonBusan.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.dto.JoinDto;
import com.example.comeonBusan.entity.UserEntity;
import com.example.comeonBusan.repository.UserRepository;

@Service
public class JoinService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void joinProcess(JoinDto joinDto) {
		
		String username = joinDto.getUsername();
		String password = joinDto.getPassword();
		
		System.out.println(username);
		System.out.println(password);
		
		Boolean isExist = userRepository.existsByUsername(username);
		
		if(isExist) {
			
			return;
			
		}
		
		UserEntity data = new UserEntity();
		
		data.setUsername(username);
		data.setPassword(bCryptPasswordEncoder.encode(password));
		data.setRole("ROLE_ADMIN");
		
		
		userRepository.save(data);
		
		
		}
		
	}
