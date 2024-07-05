package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TestController {

	@RequestMapping("/test")
	public String root() {
		
		return "test";
				
	}
	
	@GetMapping("/")
	public String root2() {
		return "index";
		
	}
	
	@RequestMapping("/kibTest")
	public String kibTest() {
		return "kibTest";
	}
	
	@RequestMapping("/tourDetail")
	public String tourDetailPage(@RequestParam("uc_seq")String uc_seq, HttpServletResponse response) {
		response.addHeader("uc_seq", uc_seq);
		
		return "tourDetail";
	}
}
