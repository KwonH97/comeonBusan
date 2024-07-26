package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@GetMapping("/testNotice")
	public String testNotice() {
		return "/notice/testNotice";
	}
	
	// 공지사항 
	
	@RequestMapping("/noticeList")
	public String noticeList() {
		
		return "/notice/noticeList";
	}
	
	@RequestMapping("/noticeDetail")
	public String noticeDetail(Model model, @RequestParam("hnum") String hnum) {
		
		model.addAttribute("id", hnum);
	
		return "/notice/noticeDetail";
		
	}
	
	@RequestMapping("/noticeAdd")
	public String noticeAdd(){
		
		return "/notice/noticeAdd";
		
	}
		
	
	
	@RequestMapping("/noticeModify")
	public String noticeModify(Model model, @RequestParam("hnum") String hnum) {
		
		System.out.println(hnum);
		
		model.addAttribute("id", hnum);
		
		return "/notice/noticeModify";
	}
}
