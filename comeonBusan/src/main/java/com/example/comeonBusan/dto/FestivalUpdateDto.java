package com.example.comeonBusan.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FestivalUpdateDto {
	
		private Long ucSeq;
	    private String maintitle;
	    private String title;
	    private String subtitle;
	    private String mainPlace;
	    private String place;
	    private String itemcntnts;
	    private String addr1;
	    private String addr2;
	    private String gugunNm;
	    private String cntctTel;
	    private String homepageUrl;
	    private String usageDayWeekAndTime;
	    private String startDate;
	    private String endDate;
	    private String usageDay;
	    private String usageAmount;
	    private String middleSizeRm1;
	    private String trfcInfo;
	    private String lat;
	    private String lng;
	    private MultipartFile file; // 파일 필드 추가

	
	
}
