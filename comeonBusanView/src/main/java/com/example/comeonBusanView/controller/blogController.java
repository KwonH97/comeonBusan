package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class blogController {

	@GetMapping("/blog")
	public String getblog() {
		
		return "/searchblog/blog";
	}
}
