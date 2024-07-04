package com.example.comeonBusan.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Lodgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lid; // 자동 증가 번호

    private String 업체명;
    private String 카테고리명;
    private String 필지고유번호;
    private String 시도명;
    private String 시군구명;
    private String 읍면동명;
    private String 리명;
    private String 번지;
    private String 법정동코드;
    private String 행정동코드;
    private String 도로명코드;
    private String 도로명;
    private String 도로명상세;
    private Double 경도;
    private Double 위도;
    private String 폐업여부;
    private String 전화번호;
    private String 홈페이지주소;
    private String 주차가능여부;
    private String 화장실유무;
    private String 화장실타입;
    private String 수유실유무;
    private String 물품보관함유무;
    private String 유아거치대유무;
    private String 휠체어이동가능여부;
    private String 점자유도로유무;
    private LocalDate 등록일자;

    @OneToMany(mappedBy = "lodgment", cascade = CascadeType.ALL)
    private List<Likes> likes;
    
    @OneToMany(mappedBy = "lodgment", cascade = CascadeType.ALL)
    private List<Views> views;
}
