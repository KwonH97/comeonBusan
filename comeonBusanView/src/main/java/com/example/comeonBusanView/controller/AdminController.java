package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/enterEmail")
	public String enterEmail(Model model, @RequestParam("username") String username) {
		
		model.addAttribute("username", username);
		
		return "/admin/enterEmail";
		
	}
	
	@RequestMapping("/reset-password/{uuid}")
	public String resetPassword(@PathVariable("uuid") String uuid, Model model) {
		
		System.out.println("reset-password uuid................" + uuid);
		model.addAttribute("uuid", uuid);
		
		return "/admin/resetPassword";
		
	}
	
	@RequestMapping("/dashboard")
	public String dashboard() {
		
		return "/admin/dashboard";
	}
	
	
}
