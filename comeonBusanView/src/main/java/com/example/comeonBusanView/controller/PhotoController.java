package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PhotoController {

	@RequestMapping("/regi_photo")
	public String regiPhoto() {
		return "/photo/regi_photo";
	}
	
	@RequestMapping("/modi_photo")
	public String modifyPhoto(@RequestParam("pno")String pno, Model model) {
		model.addAttribute("pno", pno);
		
		return "/photo/modi_photo";
	}
	
	@RequestMapping("/photoList")
	public String photoList() {
		return "/photo/photoList";
	}

}
