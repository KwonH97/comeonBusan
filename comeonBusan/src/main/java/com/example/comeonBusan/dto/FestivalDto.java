package com.example.comeonBusan.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FestivalDto {

	private String place;
	private String mainImgThumb;
	private Date startDate;
	private Date endDate;

	public FestivalDto(String place, String mainImgThumb, Date startDate, Date endDate) {

		this.place = place;
		this.mainImgThumb = mainImgThumb;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	
}
