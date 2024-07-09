package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String tourDetailPage(@RequestParam("uc_seq")String uc_seq, Model model) {
		model.addAttribute("uc_seq", uc_seq);
		
		return "tourDetail";
	}
	
	@RequestMapping("/regi_tour")
	public String registTour() {
		return "/admin/regi_tour";
	}
	
	@RequestMapping("/modi_tour")
	public String modify_tourlist(@RequestParam("uc_seq")String uc_seq,Model model) {
		model.addAttribute("uc_seq",uc_seq);
		
		return "/admin/modi_tour";
	}
	
	@RequestMapping("/foodList")
	public String foodTourList() {
		return "foodList";
	}
	
}
