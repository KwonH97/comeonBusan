package com.example.comeonBusan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.Lodgment;
import com.example.comeonBusan.service.LodgmentService;

@RestController
public class LodgmentController {

	@Autowired
	private LodgmentService lodgmentService;
	
	@GetMapping("/lodgment")
	public List<Lodgment> getAllLodgment(){
		
		return lodgmentService.getLodgmentList();
	}
	
	@GetMapping("/lodgment/page")
	public Page<Lodgment> getPage(@RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="size",defaultValue = "10") int size){
		
		return lodgmentService.LodgmentPage(PageRequest.of(page, size));
	}
	
	@PostMapping("/lodgment")
	public ResponseEntity<String> saveLodgment(@ModelAttribute Lodgment lodgment) {
		lodgmentService.saveLodgment(lodgment);
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}
}
