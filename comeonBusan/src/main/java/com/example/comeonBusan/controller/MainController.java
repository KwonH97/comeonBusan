package com.example.comeonBusan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/good")
	public String mainP() {
		
		
		return "main";
	}
	
}
