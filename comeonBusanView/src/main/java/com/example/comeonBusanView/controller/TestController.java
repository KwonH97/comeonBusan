package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
