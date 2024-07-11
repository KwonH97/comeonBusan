package com.example.comeonBusan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="photo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pno; 
	
	private String main_img_normal; //기본 파일
	
	private String main_img_thumb; //썸네일
	
	private String title; //사진 제목
	 
	private String shoot_year; //촬영연도
	
	private String shooter; // 촬영기관(개인 포함) = 촬영자
	
	private String have_agency; // 소장기관
	
	private String hashtag; //해쉬태그
}
