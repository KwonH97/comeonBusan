package com.example.comeonBusan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@GetMapping("/admin")
	public ResponseEntity<String> getAdminPage() {
		try {
			// JWT 토큰 검증 로직이 여기에 필요할 수 있습니다.
			// 예시: 인증된 사용자만 접근 가능하도록 설정

			return ResponseEntity.ok("Admin Page Contents");
		} catch (Exception e) {
			// 오류가 발생한 경우 로그를 남기고 500 응답을 반환
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal Server Error: " + e.getMessage());
		}
	}

}
