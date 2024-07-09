package com.example.comeonBusan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.Lodgment;
import com.example.comeonBusan.service.LodgmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LodgmentController {

	@Autowired
	private LodgmentService lodgmentService;
	
	@GetMapping("/lodgment")
	public List<Lodgment> getAllLodgment(){
		
		return lodgmentService.getLodgmentList();
	}
	
	@GetMapping("/lodgment/page")
	public Page<Lodgment> getPage(@RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="size",defaultValue = "10") int size){
		log.info("Page request received: page={}, size={}", page, size); // 로그 추가
		return lodgmentService.LodgmentPage(PageRequest.of(page, size));
	}
}
