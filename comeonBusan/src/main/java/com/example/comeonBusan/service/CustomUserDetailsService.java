package com.example.comeonBusan.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.dto.CustomUserDetails;
import com.example.comeonBusan.entity.UserEntity;
import com.example.comeonBusan.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 UserEntity user = userRepository.findByUsername(username);
		 
		 if (user == null) {
	            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
	        }
	        return new CustomUserDetails(user); // CustomUserDetails 객체를 반환합니다.
	    }

	
}
