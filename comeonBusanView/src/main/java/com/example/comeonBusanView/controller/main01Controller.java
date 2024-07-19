package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class main01Controller {

	@RequestMapping("/main01")
	public String main01() {
		return "/mainPage/main01";
	}
}
