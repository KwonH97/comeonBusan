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

	public String joinProcess(JoinDto joinDto) {

		String username = joinDto.getUsername();
		String password = joinDto.getPassword();
		String email = joinDto.getEmail();

		System.out.println(username);
		System.out.println(password);
		System.out.println(email);

		Boolean isExistUsername = userRepository.existsByUsername(username);
		Boolean isExistEmail = userRepository.existsByEmail(email);
		System.out.println("username 존재하는지 불린 결과" + isExistUsername);

		if (isExistUsername) {

			return "이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요";

		} else {

			if (isExistEmail) {

				return "이미 가입된 이메일입니다. 다른 이메일을 입력해주세요";

			} else {

				UserEntity data = new UserEntity();

				data.setUsername(username);
				data.setPassword(bCryptPasswordEncoder.encode(password));
				data.setEmail(email);
				data.setRole("ROLE_ADMIN");

				userRepository.save(data);

				return "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.";
			}
		}
	}

}
