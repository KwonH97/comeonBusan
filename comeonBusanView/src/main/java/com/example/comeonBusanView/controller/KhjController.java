package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class KhjController {

	// admin 회원가입, 로그인
	
	@RequestMapping("/joinForm")
	public String joinForm() {
		
		return "/admin/joinForm";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		
		return "/admin/loginForm";
	}
	
	
	// 축제 festival

	@RequestMapping("/festivalList")
	public String festivalList() {
		
		return "festivalList";
		
	}
	
	@RequestMapping("/festivalDetail")
	public String festivalDetail(@RequestParam("uc_seq") String uc_seq, Model model) {
		
		System.out.println(uc_seq);
		
		model.addAttribute("uc_seq", uc_seq);		
		
		return "festivalDetail";
	}
	
	// 축제 admin 등록, 수정
	
	@RequestMapping("/festivalModify")
	public String festivalModify(Model model, @RequestParam("uc_seq") String uc_seq) {

		
		System.out.println("festivalModify ............... viewController");
		
		System.out.println(uc_seq);
		
		model.addAttribute("uc_seq", uc_seq);

		return "/admin/festivalModify";
	}
	
	
	// 공지사항 
	
	@RequestMapping("/noticeList")
	public String noticeList() {
		
		return "noticeList";
	}
	
	@RequestMapping("/noticeDetail")
	public String noticeDetail(Model model, @RequestParam("hnum") String hnum) {
		
		model.addAttribute("id", hnum);
	
		return "noticeDetail";
		
	}
	
	@RequestMapping("/noticeAdd")
	public String noticeAdd(){
		
		return "/admin/noticeAdd";
		
	}
		
	
	
	@RequestMapping("/noticeModify")
	public String noticeModify(Model model, @RequestParam("hnum") String hnum) {
		
		System.out.println(hnum);
		
		model.addAttribute("id", hnum);
		
		return "/admin/noticeModify";
	}
}
