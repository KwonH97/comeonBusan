package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TourController {

	@RequestMapping("/tourList")
	public String kibTest() {
		return "/tour/tourList";
	}
	
	@RequestMapping("/tourDetail")
	public String tourDetailPage(@RequestParam("uc_seq")String uc_seq, Model model) {
		model.addAttribute("uc_seq", uc_seq);
		
		return "/tour/tourDetail";
	}
	
	@RequestMapping("/regi_tour")
	public String registTour() {
		return "/tour/regi_tour";
	}
	
	@RequestMapping("/modi_tour")
	public String modify_tourlist(@RequestParam("uc_seq")String uc_seq,Model model) {
		model.addAttribute("uc_seq",uc_seq);
		
		return "/tour/modi_tour";
	}
	
}
