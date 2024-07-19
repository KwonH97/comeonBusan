//package com.example.comeonBusan.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.comeonBusan.service.ViewCountService;
//
//@RestController
//@RequestMapping("/views")
//public class ViewCountController {
//
//	@Autowired
//	private ViewCountService viewCountService;
//
//	// 조회수 증가 엔드포인트
//	@PostMapping("/tour/{uc_seq}")
//	public void incrementTourViewCount(@PathVariable("uc_seq") String uc_seq) {
//		viewCountService.incrementTourViewCount(uc_seq);
//	}
//
//	@PostMapping("/festival/{uc_seq}")
//	public void incrementFestivalViewCount(@PathVariable("uc_seq") Long uc_seq) {
//		viewCountService.incrementFestivalViewCount(uc_seq);
//	}
//
//	@PostMapping("/food/{uc_seq}")
//	public void incrementFoodViewCount(@PathVariable("uc_seq") String uc_seq) {
//		viewCountService.incrementFoodViewCount(uc_seq);
//	}
//
//	@GetMapping("/tour/{uc_seq}")
//	public Long getTourViewCount(@PathVariable("uc_seq") String uc_seq) {
//		return viewCountService.getViewCountForTour(uc_seq);
//	}
//
//	@GetMapping("/festival/{uc_seq}")
//	public Long getFestivalViewCount(@PathVariable("uc_seq") Long uc_seq) {
//		return viewCountService.getViewCountForFestival(uc_seq);
//	}
//
//	@GetMapping("/food/{uc_seq}")
//	public Long getFoodViewCount(@PathVariable("uc_seq") String uc_seq) {
//		return viewCountService.getViewCountForFood(uc_seq);
//	}
//
//	// 조회수 가져오기 엔드포인트
////	@GetMapping("/count")
////	public Long getViewCount(@RequestParam String tourlistUcSeq, @RequestParam Long festivalUcSeq,
////			@RequestParam String foodUcSeq) {
////		return viewCountService.getViewCount(tourlistUcSeq, festivalUcSeq, foodUcSeq);
////	}
//}
