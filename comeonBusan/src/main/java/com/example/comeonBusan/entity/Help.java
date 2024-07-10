package com.example.comeonBusan.entity;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Help {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hnum;
	private String title;
	private String content;
	private String himg; // 이미지 URL
	private String hThumbnailImg; // 썸네일 이미지 URL
	private LocalDate regDate;
	
	public void update(Help help) {
		
		this.title = help.getTitle();
		this.content = help.getContent();
		this.himg = help.getHimg();
		this.hThumbnailImg = help.getHThumbnailImg();
		this.regDate = help.getRegDate();
	}
	
	private static ModelMapper modelMapper = new ModelMapper();
}
