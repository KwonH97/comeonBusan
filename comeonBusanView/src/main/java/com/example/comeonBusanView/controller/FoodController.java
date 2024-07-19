package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FoodController {
	
	@RequestMapping("/foodList")
	public String foodTourList() {
		return "/food/foodList";
	}
	

	@RequestMapping("/foodDetail")
	public String foodTourDetail(@RequestParam("uc_seq")String uc_seq, Model model) {
		model.addAttribute("uc_seq", uc_seq);
		
		return "/food/foodDetail";
	}
	
	@RequestMapping("/regi_food")
	public String regiFoodTour() {
		return "/food/regi_food";
	}
	
	@RequestMapping("/modi_food")
	public String modifyFoodTour(@RequestParam("uc_seq")String uc_seq, Model model) {
		model.addAttribute("uc_seq", uc_seq);
		return "/food/modi_food";
	}
}
