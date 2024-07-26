package com.example.comeonBusan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.Inquiry;
import com.example.comeonBusan.repository.InquiryRepository;

@RestController
public class InquiryController {

	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	@GetMapping("/getInquiryInfo")
	public List<Inquiry> getInquiryInfo() {
		
		
		inquiryRepository.findAll();
		
		// 문의사항
		// 전부조회
		// 아님
		// 미답변 문의사항만
		
		List<Inquiry> list = inquiryRepository.findInquiryStateIsZero();
		
		System.out.println(list);
		
		
		return list;
		
		
	}
	
}
