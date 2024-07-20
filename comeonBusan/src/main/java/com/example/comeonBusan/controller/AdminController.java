package com.example.comeonBusan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
       }
   }
	
	@PostMapping("/emailCheck/{username}")
	public ResponseEntity<String> emailCheck(@PathVariable("username") String username){
		
		// 폼데이터 받기
		// db조회
		// 해당 아이디의 이메일이 맞으면 전송
		// 해당 아이디에 그런 이메일이 없으면 정확한 이메일을 입력해달라고 하기
		// 아이디 입력하는 부분에도 db한번 갔다오기(입력한 아이디가 존재하면 패스)
		// 
		
		
		 return ResponseEntity.ok("해당 이메일에서 새 비밀번호 설정 메일을 확인해주세요");
		
	}
}
