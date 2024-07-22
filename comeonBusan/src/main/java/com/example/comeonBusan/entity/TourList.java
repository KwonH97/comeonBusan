package com.example.comeonBusan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tourlist")
public class TourList {

    @Id
    @Column(name = "uc_seq", nullable = false)
    private String uc_seq; // 콘텐츠 id

    private String maintitle; // 콘텐츠 명
    private String gugun_nm; // 구군
    private String lat; // 위도
    private String lng; // 경도
    private String place; // 관광지 이름
    private String title; // 관광지 홍보 타이틀
    private String subtitle; // 관광지 홍보 부제목
    private String addr; // 관광지 주소
    private String tel; //관광지 연락처
    private String homepage_url; //관광지 홈페이지 주소

    @Column(columnDefinition = "TEXT")
    private String trfc_info; // 관광지 교통정보
    private String usage_day; //관광지 운영일
    private String hldy_info; //관광지 휴무일
    private String useageDay_week_and_time; // 운영 및 시간
    private String usage_amount; //이용요금
    private String middle_size_rm; //편의시설
    private String main_img_normal; //이미지URL
    private String main_img_thumb; //썸네일이미지 URL

    @Column(columnDefinition = "TEXT")
    private String ITEMCNTNTS; // 관광지 상세내용

    @OneToMany(mappedBy = "tourlist", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Likes> likes;
    
    @OneToMany(mappedBy = "tourlist", cascade = CascadeType.ALL)
    private List<Views> views;
}
