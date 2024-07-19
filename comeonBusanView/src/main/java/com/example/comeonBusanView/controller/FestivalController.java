package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/festival")
public class FestivalController {
	
	// 축제 festival

	@RequestMapping("/festivalList")
	public String festivalList() {
		
		return "/festival/festivalList";
		
	}
	
	@RequestMapping("/festivalDetail")
	public String festivalDetail(@RequestParam("uc_seq") String uc_seq, Model model) {
		
		System.out.println(uc_seq);
		
		model.addAttribute("uc_seq", uc_seq);		
		
		return "/festival/festivalDetail";
	}
	
	// 축제 admin 등록, 수정
	
	@RequestMapping("/festivalAdd")
	public String festivalAdd() {
		
		return "/festival/festivalAdd";		
	}
	
	@RequestMapping("/festivalModify")
	public String festivalModify(Model model, @RequestParam("uc_seq") String uc_seq) {

		
		System.out.println("festivalModify ............... viewController");
		
		System.out.println(uc_seq);
		
		model.addAttribute("uc_seq", uc_seq);

		return "/festival/festivalModify";
	}
}
