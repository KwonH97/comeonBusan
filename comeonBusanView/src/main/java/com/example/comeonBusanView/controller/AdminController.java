package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
