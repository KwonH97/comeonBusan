package com.example.comeonBusan.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	
	private String main_img_normal;
	
	private String main_img_thumb;
	
	@Column(columnDefinition = "TEXT")
	private String itemcntnts;
	
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
	@ToString.Exclude
    private List<Likes> likes;
    
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Views> views;
	
}
