package com.example.comeonBusan.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.dto.InquiryRequest;
import com.example.comeonBusan.entity.Inquiry;
import com.example.comeonBusan.repository.InquiryRepository;

@RestController
public class InquiryController {

	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	@GetMapping("/getInquiryInfo")
	public List<Inquiry> getInquiryInfo() {
		
		List<Inquiry> list = inquiryRepository.findInquiryStateIsZero();
		
		System.out.println(list);
		
		return list;
		
		
	}
	
	@PostMapping("/markAsAnswered")
	public ResponseEntity<?> markAsAnswered(@RequestBody String id) {

		//System.out.println(id);
		
		//Long ino_long = Long.parseLong(id);
		//inquiryRepository.updateStateByIno(ino_long);
		
		
		try {
            Long inquiryId = Long.parseLong(id);
            System.out.println("InquiryId " + inquiryId);
            inquiryRepository.updateStateByIno(inquiryId);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
		
	}
	
	@PostMapping("/InquiryPost")
	public ResponseEntity<String> inquiryPost(@RequestBody InquiryRequest request){
		
		// 이메일 및 메시지 처리 로직
		
		
		Inquiry inquiry = new Inquiry();
		
		inquiry.setInquiry(request.getMessage());
		inquiry.setEmail(request.getEmail());
		inquiry.setRegDate(LocalDate.now());
		
		inquiryRepository.save(inquiry);
		
		return ResponseEntity.ok("문의가 성공적으로 전송되었습니다.");
		
	}

	
}
