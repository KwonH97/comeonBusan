package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

	@GetMapping("/main")
	public String main() {
		return "/fragments/aaa";
	}
}
