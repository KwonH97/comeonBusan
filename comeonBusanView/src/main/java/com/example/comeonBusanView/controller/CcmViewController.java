package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CcmViewController {
	
	@GetMapping("/weather")
	public String loadMap() {
		return "weather";
	}

}
