//package com.example.comeonBusanView.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//@Controller
//public class TestController {
//
//	@RequestMapping("/test")
//	public String root() {
//		
//		return "test";
//				
//	}
//	
//	@GetMapping("/")
//	public String root2() {
//		return "index";
//		
//	}
//	
//	@RequestMapping("/tourList")
//	public String kibTest() {
//		return "tourList";
//	}
//	
//	@RequestMapping("/tourDetail")
//	public String tourDetailPage(@RequestParam("uc_seq")String uc_seq, Model model) {
//		model.addAttribute("uc_seq", uc_seq);
//		
//		return "tourDetail";
//	}
//	
//	@RequestMapping("/regi_tour")
//	public String registTour() {
//		return "/admin/regi_tour";
//	}
//	
//	@RequestMapping("/modi_tour")
//	public String modify_tourlist(@RequestParam("uc_seq")String uc_seq,Model model) {
//		model.addAttribute("uc_seq",uc_seq);
//		
//		return "/admin/modi_tour";
//	}
//	
//	@RequestMapping("/foodList")
//	public String foodTourList() {
//		return "foodList";
//	}
//	
//
//	@RequestMapping("/foodDetail")
//	public String foodTourDetail(@RequestParam("uc_seq")String uc_seq, Model model) {
//		model.addAttribute("uc_seq", uc_seq);
//		
//		return "foodDetail";
//	}
//	
//	@RequestMapping("/regi_food")
//	public String regiFoodTour() {
//		return "/admin/regi_food";
//	}
//	
//	@RequestMapping("/modi_food")
//	public String modifyFoodTour(@RequestParam("uc_seq")String uc_seq, Model model) {
//		model.addAttribute("uc_seq", uc_seq);
//		return "/admin/modi_food";
//	}
//	
//	@RequestMapping("/regi_photo")
//	public String regiPhoto() {
//		return "/admin/regi_photo";
//	}
//	
//	@RequestMapping("/modi_photo")
//	public String modifyPhoto(@RequestParam("pno")String pno, Model model) {
//		model.addAttribute("pno", pno);
//		
//		return "/admin/modi_photo";
//	}
//	
//	@RequestMapping("/photoList")
//	public String photoList() {
//		return "photoList";
//	}
//
//}
