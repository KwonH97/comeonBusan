package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CcmViewController {
	
	@GetMapping("/weather")
	public String loadWeather() {
		return "weather";
	}
	
	@GetMapping("/showMap")
	public String showMap() {
		return "mapTest";
	}
	
	@GetMapping("/showTheWay")
	public String showTheWay() {
		return "showTheWay";
	}
	
	@GetMapping("/roadView")
	public String roadView() {
		return "roadView";
	}
	
	@GetMapping("/rainInfo")
	public String rainInfo() {
		return "rainInfo";
	}
	
	@GetMapping("/recommand")
	public String recommand() {
		return "recommand";
	}
	
	@GetMapping("/translate")
	public String translate() {
		return "translate";
	}
	
	@GetMapping("/dangerArea")
	public String dangerArea() {
		return "dangerArea";
	}


}
