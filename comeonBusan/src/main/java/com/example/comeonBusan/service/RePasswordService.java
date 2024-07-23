package com.example.comeonBusan.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comeonBusan.entity.UserEntity;
import com.example.comeonBusan.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RePasswordService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RedisService redisService;

	@Transactional
	public void resetPassword(String uuid, String newPassword) throws Exception {

		// redis에 uuid가 있는지 확인, 없으면 error

		String email = redisService.getValues(uuid);
		if (email == null) {
			throw new Exception();

		}

		// redis에서 uuid로 이메일을 찾아온다.
		Optional<UserEntity> user = userRepository.findByEmail(email);
		
		UserEntity result =  user.get();
		
		// 비밀번호 재설정
		result.updatePassword(passwordEncoder.encode((newPassword)));

		// 비밀번호 업데이트 후 redis에서 uuid를 지운다.
		redisService.deleteValues(uuid);

	}

}
