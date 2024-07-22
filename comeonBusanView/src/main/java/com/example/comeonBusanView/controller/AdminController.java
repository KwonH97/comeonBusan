package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

	// admin 회원가입, 로그인
	
	@RequestMapping("/joinForm")
	public String joinForm() {
		
		return "/admin/joinForm";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		
		return "/admin/loginForm";
	}
	
	@RequestMapping("/adminPage")
	public String adminPage() {
		
		return "/admin/adminPage";
	}
	
	@RequestMapping("/newsLetter")
	public String newsLetter() {
		
		return "/admin/newsLetter";
	}
	
	// 새 비밀번호 설정 : enterId -> enterEmail -> reset-password
	
	@RequestMapping("/enterId")
	public String setNewPw() {
		
		return "/admin/enterId";
		
	}
	
	@PostMapping("/enterEmail")
	public String enterEmail(Model model, @RequestParam("username") String username) {
		
		model.addAttribute("username", username);
		
		return "/admin/enterEmail";
		
	}
	
	@RequestMapping("/reset-password")
	public String resetPassword() {
		
		
		return "/admin/resetPassword";
		
	}
	
	
	
}
