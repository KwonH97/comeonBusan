package com.example.comeonBusan.entity;

import jakarta.persistence.Column;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="tourlist")
public class ExEntity {
	
	@Id
	@Column(name="uc_seq",nullable=false)
	private String UC_SEQ;//콘텐츠 id
	
	private String maintitle;//콘텐츠 명
	
	private String gugun_nm;//구군
	
	private String lat;//위도
	
	private String lng;//경도
	
	private String place;
	
	private String title;
	
	private String subtitle;
	
	private String addr;
	
	private String tel;
	
	private String homepage_url;
	
	@Column(columnDefinition = "TEXT")
	private String trfc_info;
	
	private String usage_day;
	
	private String hldy_info;
	
	private String useageDay_week_and_time;
	
	private String usage_amount;
	
	private String middle_size_rm;
	
	private String main_img_normal;
	
	private String main_img_thumb;
	
	@Column(columnDefinition = "TEXT")
	private String ITEMCNTNTS;
	
	
}
