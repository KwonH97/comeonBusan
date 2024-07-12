package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
	
	@GetMapping("/searchResult")
	public String searchResult() {
		return "/searchResult";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index2";
	}
}
