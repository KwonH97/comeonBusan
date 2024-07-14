package com.example.comeonBusan.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "festival")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Festival implements Serializable {

	@Id
	@Column(name = "UC_SEQ")
	private Long ucSeq; // 콘텐츠 id

	@Column(name = "MAIN_TITLE", length = 255)
	private String mainTitle; // 콘텐츠명

	@Column(name = "LNG")
	private Double lng; // 경도

	@Column(name = "MIDDLE_SIZE_RM1", columnDefinition = "TEXT")
	private String middleSizeRm1; // 편의시설

	@Column(name = "USAGE_AMOUNT", length = 255)
	private String usageAmount; // 이용요금

	@Column(name = "CNTCT_TEL", length = 50)
	private String cntctTel; // 연락처

	@Column(name = "MAIN_IMG_NORMAL", columnDefinition = "TEXT")
	private String mainImgNormal; // 이미지 URL

	@Column(name = "TRFC_INFO", columnDefinition = "TEXT")
	private String trfcInfo; // 교통정보

	@Column(name = "ITEMCNTNTS", columnDefinition = "TEXT")
	private String itemCntnts; // 상세내용

	@Column(name = "PLACE", length = 255)
	private String place; // 장소

	@Column(name = "SUBTITLE", length = 255)
	private String subtitle; // 부제목

	@Column(name = "USAGE_DAY", length = 255)
	private String usageDay; // 운영기간

	@Column(name = "ADDR2", length = 255)
	private String addr2; // 주소 기타

	@Column(name = "USAGE_DAY_WEEK_AND_TIME", length = 255)
	private String usageDayWeekAndTime; // 이용요일 및 시간

	@Column(name = "GUGUN_NM", length = 255)
	private String gugunNm; // 구군

	@Column(name = "ADDR1", length = 255)
	private String addr1; // 주소

	@Column(name = "HOMEPAGE_URL", columnDefinition = "TEXT")
	private String homepageUrl; // 홈페이지

	@Column(name = "TITLE", length = 255)
	private String title; // 제목

	@Column(name = "MAIN_PLACE", length = 255)
	private String mainPlace; // 주요장소

	@Column(name = "MAIN_IMG_THUMB", columnDefinition = "TEXT")
	private String mainImgThumb; // 썸네일 이미지 URL

	@Column(name = "LAT")
	private Double lat; // 위도

    @Temporal(TemporalType.DATE)
	@Column(name = "startDate")
	private Date startDate; // 시작일
    
    @Temporal(TemporalType.DATE)
	@Column(name = "endDate")
	private Date endDate; // 종료일
	
	
	@OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
	private List<Likes> likes;

	@OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
	private List<Views> views;

	public static double parseDoubleOrDefault(String value, double defaultValue) {

		try {

			if (value == null || value.trim().isEmpty()) {
				return defaultValue;
			}
			return Double.parseDouble(value);

		} catch (NumberFormatException e) {
			return defaultValue;
		}

	}
	
}
