package com.example.comeonBusan.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "festival")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Festival implements Serializable {

    @Id  
    @Column(name = "UC_SEQ")
    private Long ucSeq;

    @Column(name = "MAIN_TITLE", length = 255)
    private String mainTitle;

    @Column(name = "LNG")
    private Float lng;

    @Column(name = "MIDDLE_SIZE_RM1", columnDefinition = "TEXT")
    private String middleSizeRm1;

    @Column(name = "USAGE_AMOUNT", length = 255)
    private String usageAmount;

    @Column(name = "CNTCT_TEL", length = 50)
    private String cntctTel;

    @Column(name = "MAIN_IMG_NORMAL", columnDefinition = "TEXT")
    private String mainImgNormal;

    @Column(name = "TRFC_INFO", columnDefinition = "TEXT")
    private String trfcInfo;

    @Column(name = "ITEMCNTNTS", columnDefinition = "TEXT")
    private String itemCntnts;

    @Column(name = "PLACE", length = 255)
    private String place;

    @Column(name = "SUBTITLE", length = 255)
    private String subtitle;

    @Column(name = "USAGE_DAY", length = 255)
    private String usageDay;

    @Column(name = "ADDR2", length = 255)
    private String addr2;

    @Column(name = "USAGE_DAY_WEEK_AND_TIME", length = 255)
    private String usageDayWeekAndTime;

    @Column(name = "GUGUN_NM", length = 255)
    private String gugunNm;

    @Column(name = "ADDR1", length = 255)
    private String addr1;

    @Column(name = "HOMEPAGE_URL", columnDefinition = "TEXT")
    private String homepageUrl;

    @Column(name = "TITLE", length = 255)
    private String title;

    @Column(name = "MAIN_PLACE", length = 255)
    private String mainPlace;

    @Column(name = "MAIN_IMG_THUMB", columnDefinition = "TEXT")
    private String mainImgThumb;

    @Column(name = "LAT")
    private Float lat;
}