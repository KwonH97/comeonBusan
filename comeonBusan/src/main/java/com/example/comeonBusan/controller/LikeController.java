package com.example.comeonBusan.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comeonBusan.entity.Festival;
import com.example.comeonBusan.entity.Food;
import com.example.comeonBusan.entity.Likes;
import com.example.comeonBusan.entity.TourList;
import com.example.comeonBusan.repository.LikeRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/like")
@Slf4j
public class LikeController {

	@Autowired
	private LikeRepository likeRepository;
	
	@GetMapping("/like/{uc_seq}")
	public Long getLikeCount(@PathVariable("uc_seq") String uc_seq) {

		System.out.println("getLikeCount..................");

		Long uc_seq_long = Long.parseLong(uc_seq);

		System.out.println(uc_seq_long);

		Long likeCount = likeRepository.findLikeCountByFestivalUcSeq(uc_seq_long);

		System.out.println("디비에서 찾아낸 특정 uc_seq 의 좋아요 수........." + likeCount);

		return likeCount;

	}
	
	@PostMapping("/doLikeFestival/{uc_seq}")
	public String doLikeFestival(@PathVariable("uc_seq") String uc_seq) {

		System.out.println("doLike...............");

		// String likeToken = request.getHeader("Like" + uc_seq);
		// System.out.println("likeToken = " + likeToken);

		Likes like = new Likes();
		Festival festival = new Festival();
		Long uc_seq_long = Long.parseLong(uc_seq);
		festival.setUcSeq(uc_seq_long);
		like.setFestival(festival);

		Optional<Likes> existingLike = likeRepository.findByFestivalUcSeq(uc_seq_long);

		System.out.println("existingLike = " + existingLike);

		if (existingLike.isPresent()) {
			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() + 1);
			likeRepository.save(fromDB);

			return "좋아요 추가되었습니다.";

		} else {
			// 좋아요가 없는 경우 1 추가
			Likes newLike = Likes.builder().festival(Festival.builder().ucSeq(uc_seq_long).build()).likecount(1L)
					.build();

			likeRepository.save(newLike);

			return "당신이 처음으로 좋아요를 눌렀어..!";
		}

	}

	@DeleteMapping("/deleteLikeFestival/{uc_seq}")
	public String deleteLikeFestival(@PathVariable("uc_seq") String uc_seq) {

		System.out.println("deleteLike.............");

		Long uc_seq_long = Long.parseLong(uc_seq);

		Optional<Likes> existingLike = likeRepository.findByFestivalUcSeq(uc_seq_long);

		System.out.println("existingLike = " + existingLike);

		if (existingLike.isPresent()) {

			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() - 1);
			likeRepository.save(fromDB);

			return "좋아요가 취소되었습니다.";
		} else {

			// 좋아요가 없는 경우
			return "좋아요 삭제 에러";

		}
	}
	
	
	@GetMapping("/GetlikeTour/{uc_seq}")
	public Long getLikeTour(@PathVariable("uc_seq")String uc_seq) {
		
		Long likeCount = likeRepository.findLikeCountByTourUcSeq(uc_seq);
		
		System.out.println("디비에서 찾아낸 특정 uc_seq 의 좋아요 수........." + likeCount);
		return likeCount;
	}
	
	
	@PostMapping("/doLikeTour/{uc_seq}")
	public String doLikeTour(@PathVariable("uc_seq")String uc_seq) {
		Likes like = new Likes();
		TourList tour = new TourList();
		tour.setUc_seq(uc_seq);
		like.setTourlist(tour);
		
		
		Optional<Likes> existingLike = likeRepository.findByTourUcSeq(uc_seq);
		
		if(existingLike.isPresent()) {
			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() + 1);
			likeRepository.save(fromDB);
			return "좋아요 추가되었습니다.";
		}else {
			Likes newLike = Likes.builder().tourlist(TourList.builder().uc_seq(uc_seq).build()).likecount(1L)
					.build();

			likeRepository.save(newLike);

			return "당신이 처음으로 좋아요를 눌렀어..!";
		}
		
	}
	
	@DeleteMapping("deleteLikeTour/{uc_seq}")
	public String deleteLikeTour(@PathVariable("uc_seq")String uc_seq) {
		
		Optional<Likes> existingLike = likeRepository.findByTourUcSeq(uc_seq);
		
		if (existingLike.isPresent()) {

			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() - 1);
			likeRepository.save(fromDB);

			return "좋아요가 취소되었습니다.";
		} else {

			// 좋아요가 없는 경우
			return "좋아요 삭제 에러";

		}
	}
	
	
	@GetMapping("/foodlike/{uc_seq}")
	public Long getFoodLike(@PathVariable("uc_seq")String uc_seq) {
		Long likeCount = likeRepository.findLikeCountByFoodUcSeq(uc_seq);
		
		return likeCount;
	}
	
	@PostMapping("/doFoodLike/{uc_seq}")
	public String doFoodLike(@PathVariable("uc_seq")String uc_seq) {
		Likes like = new Likes();
		Food food = new Food();
		
		food.setUC_SEQ(uc_seq);
		like.setFood(food);
		
		Optional<Likes> existingLike = likeRepository.findByFoodUcSeq(uc_seq);
		
		if(existingLike.isPresent()) {
			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() + 1);
			likeRepository.save(fromDB);
			return "좋아요 추가되었습니다.";
		}else {
			Likes newLike = Likes.builder().food(Food.builder().UC_SEQ(uc_seq).build()).likecount(1L)
					.build();

			likeRepository.save(newLike);

			return "당신이 처음으로 좋아요를 눌렀어..!";
		}
	}
	
	@DeleteMapping("/deleteLikeFood/{uc_seq}")
	public String deleteLikeFood(@PathVariable("uc_seq")String uc_seq) {
		
		Optional<Likes> existingLike = likeRepository.findByFoodUcSeq(uc_seq);
		
		if (existingLike.isPresent()) {

			Likes fromDB = existingLike.get();
			fromDB.setLikecount(fromDB.getLikecount() - 1);
			likeRepository.save(fromDB);

			return "좋아요가 취소되었습니다.";
		} else {

			// 좋아요가 없는 경우
			return "좋아요 삭제 에러";

		}
	}
	
}
