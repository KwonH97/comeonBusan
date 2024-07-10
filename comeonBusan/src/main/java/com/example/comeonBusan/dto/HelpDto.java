package com.example.comeonBusan.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class HelpDto {

	private String title;
	private String content;
	private MultipartFile file;
	private LocalDate regDate;
	
	public String getFileName() {
		return file.getOriginalFilename();
	}
	
}
