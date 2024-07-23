package com.example.comeonBusan.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.dto.JoinDto;
import com.example.comeonBusan.service.JoinService;

@RestController
public class JoinController {

	private final JoinService joinService;
	
	public JoinController(JoinService joinService) {

		this.joinService = joinService;
	}

	@PostMapping("/join")
	public String joinP(@RequestBody JoinDto joinDto) {
		
		String message = joinService.joinProcess(joinDto);
		
		return message;
	}
	
}
