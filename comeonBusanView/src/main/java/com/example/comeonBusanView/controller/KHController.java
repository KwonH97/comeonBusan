package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KHController {

	@GetMapping("/header")
	public String header() {
		
		return "/fragments/header";
	}
	
	@GetMapping("/searchResult")
	public String searchResult() {
		return "/searchResult";
	}
}
