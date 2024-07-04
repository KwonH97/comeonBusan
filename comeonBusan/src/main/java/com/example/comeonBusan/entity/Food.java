package com.example.comeonBusan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
	
	@Id
	@Column(name = "uc_seq", nullable = false)
    private String UC_SEQ; // 콘텐츠 id
	
	private String main_title;
	
	private String gugun_nm;
	
	private String lat; //위도
	
	private String lng; //경도
	
	private String place; 
	
	@Column(columnDefinition = "TEXT")
	private String title;
	
	private String subtitle;
	
	private String addr1;
	
	private String addr2;
	
	private String cntct_tel;
	
	private String homepage_url;
	
	private String usage_day_week_and_time;
	
	private String rprsntv_menu;
	
	private String main_img_no;
	
	private String main_img_thumb;
	
	@Column(columnDefinition = "TEXT")
	private String itemcntnts;
}
