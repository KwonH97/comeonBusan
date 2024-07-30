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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class InquiryController {

	@Autowired
	private InquiryRepository inquiryRepository;

	// 사이트 이용자가 문의사항 전송하면 등록하는 매서드
	@PostMapping("/InquiryPost")
	public ResponseEntity<String> inquiryPost(@RequestBody InquiryRequest request) {

		// 이메일 및 메시지 처리 로직

		Inquiry inquiry = new Inquiry();

		inquiry.setInquiry(request.getMessage());
		inquiry.setEmail(request.getEmail());
		inquiry.setRegDate(LocalDate.now());

		inquiryRepository.save(inquiry);

		return ResponseEntity.ok("문의가 성공적으로 전송되었습니다.");

	}

	// 미답변 문의사항 리스트 가져오는 매서드
	@GetMapping("/getInquiryInfo")
	public List<Inquiry> getInquiryInfo() {

		List<Inquiry> list = inquiryRepository.findInquiryStateIsZero();

		System.out.println(list);

		return list;

	}

	// 답변완료된 문의사항 리스트 가져오는 매서드
	@GetMapping("/getAnsweredInquiry")
	public List<Inquiry> getAnsweredInquiry() {

		List<Inquiry> list = inquiryRepository.findInquiryStateIsOne();

		System.out.println(list);

		return list;

	}

	// 답변완료 버튼 클릭 시 업데이트 되는 매서드
	@PostMapping("/markAsAnswered")
	public ResponseEntity<?> markAsAnswered(@RequestBody String request) {

		System.out.println("markAsAnswered...........");

		System.out.println(request);

		// JSON 파싱
		Gson gson = new Gson();

		// JSON 문자열을 JsonObject로 파싱
		JsonObject jo = gson.fromJson(request, JsonObject.class);

		// "id" 필드의 값을 추출
		int id = jo.get("id").getAsInt();

		System.out.println("추출된 아이디: " + id);

		// 추출된 아이디값으로 ino의 상태를 0에서 1로 업데이트 + 답변 날짜 오늘 날짜로 등록
		try {
			Long ino = Long.valueOf(id);
			System.out.println("Long ino =  " + ino);
			inquiryRepository.updateStateAndAnswerDateByIno(ino);

			return ResponseEntity.ok().build();

		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
