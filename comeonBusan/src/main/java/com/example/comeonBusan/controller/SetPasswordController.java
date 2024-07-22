package com.example.comeonBusan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.dto.EmailMessage;
import com.example.comeonBusan.entity.UserEntity;
import com.example.comeonBusan.repository.UserRepository;
import com.example.comeonBusan.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SetPasswordController {

	@Autowired
	private UserRepository userRepository;

	private final EmailService emailService;

	@GetMapping("/findAdminId")
	public ResponseEntity<String> findAdminId(@RequestParam("username") String username) {

		return ResponseEntity.ok("I wanna go ");

	}

	@PostMapping("/emailCheck")
	public ResponseEntity<String> emailCheck(@RequestParam("email") String email,
			@RequestParam("username") String username) {

		// 폼데이터 받기
		// db조회
		// 해당 아이디의 이메일이 맞으면 전송
		// 해당 아이디에 그런 이메일이 없으면 정확한 이메일을 입력해달라고 하기
		// 아이디 입력하는 부분에도 db한번 갔다오기(입력한 아이디가 존재하면 패스)
		//

		System.out.println(email);
		System.out.println(username);

		String token = generateToken();
		saveTokenToDatabase(username, token);

		// 비밀번호 설정 링크 생성
		String resetPasswordLink = "http:/localhost:8080/reset-password?token=" + token;

		String message = "<p>안녕하세요,</p>" + "<p>비밀번호 재설정을 위해 다음 링크를 클릭해주세요:</p>" + "<p><a href=\"" + resetPasswordLink
				+ "\">비밀번호 재설정 링크</a></p>";

		EmailMessage emailMessage = EmailMessage.builder().to(email).subject("[온나부산] 비밀번호 재설정").message(message)
				.build();

		try {
			emailService.sendMail(emailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("이메일 전송 중 오류가 발생했습니다.");
		}

		return ResponseEntity.ok("해당 이메일에서 새 비밀번호 설정 메일을 확인해주세요");

	}

	private String generateToken() {

		// 토큰 생성 로직
		return java.util.UUID.randomUUID().toString();
	}

	private void saveTokenToDatabase(String username, String token) {

		// 토큰 DB에 저장
		UserEntity user = userRepository.findByUsername(username);
		if (user != null) {
			user.setToken(token);
			userRepository.save(user);

		} else {
			throw new RuntimeException("User not found");

		}
	}
}