package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LodgmentController {

	@GetMapping("/lodgment")
	public String getLodgmentList() {
		return "/lodgment/listLodgment";
	}
	
	@GetMapping("/regLodgment")
	public String regLodgment() {
		return "/lodgment/regLodgment";
	}
	
	@GetMapping("/upLodgment")
	public String upLodgment() {
		return "/lodgment/upLodgment";
	}
	
	@GetMapping("/reviewLodgment")
	public String reviewLodgment() {
		return "/lodgment/reviewLodgment";
	}
}

