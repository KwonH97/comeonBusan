package com.example.comeonBusan.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.comeonBusan.dto.ResetPasswordReq;
import com.example.comeonBusan.dto.SendResetPasswordEmailReq;
import com.example.comeonBusan.dto.SendResetPasswordEmailRes;
import com.example.comeonBusan.entity.UserEntity;
import com.example.comeonBusan.repository.UserRepository;
import com.example.comeonBusan.service.EmailService;
import com.example.comeonBusan.service.RedisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SetPasswordController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final RedisService redisService;

	private final EmailService emailService;

	@PostMapping("/findAdminId")
	public String findAdminId(@RequestBody Map<String, String> request) {

		String username = request.get("username");

		// Boolean result = userRepository.existsByUsername(username);

		UserEntity user = userRepository.findByUsername(username);
		if (user.getUsername().equals(username)) {

			return username;

		} else {

			return "해당 아이디가 존재하지 않습니다.";

		}
	}

	// UUID 생성 및 이메일 전송
	@PostMapping("/send-reset-password")
	public ResponseEntity<SendResetPasswordEmailRes> sendResetPassword(
			@Validated @ModelAttribute SendResetPasswordEmailReq resetPasswordEmailReq) {

		Optional<UserEntity> user = userRepository.findByEmail(resetPasswordEmailReq.getEmail());

		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body(null); // 사용자 찾을 수 없음 처리
		}

		String uuid;

		try {
			uuid = emailService.sendResetPasswordEmail(resetPasswordEmailReq.getEmail());
		} catch (UnsupportedEncodingException e) {
			// 예외 처리 로직 추가
			return ResponseEntity.status(500).body(null);
		}

		return ResponseEntity.ok(SendResetPasswordEmailRes.builder().uuid(uuid).build());
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody ResetPasswordReq request, RedirectAttributes redirectAttributes) {
		String password = request.getPassword();
		String uuid = request.getUuid();

		String email = redisService.getValues(uuid);

		if (email == null) {

			// 유효하지 않은 또는 만료된 토큰
			// redirectAttributes.addFlashAttribute("errorMessage...", "Invalid or expired
			// token...");

			return "Invalid or expired Token....";
		}

		// 이메일 기반으로 사용자 찾기
		Optional<UserEntity> user = userRepository.findByEmail(email);

		UserEntity result = user.get();

		if (result == null) {

			// 사용자 존재하지 않음
			return "not found user";
		}

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(password);

		// 사용자 비밀번호 업데이트
		result.setPassword(encodePassword);
		userRepository.save(result);

		// Redis에서 토큰 삭제
		redisService.deleteValues(uuid);

		return "새 비밀번호가 설정되었습니다.";
	}

}