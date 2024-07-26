package com.example.comeonBusan.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ino; 
	
	private String inquiry; // 문의내용
	
	private String email; // 문의자 이메일
	
	private LocalDate regDate; // 문의등록일자
	
	private LocalDate answerDate; // 답변일자
	
    @ColumnDefault("0")
    @Builder.Default
    private Integer state = 0; // 답변여부
	
	
}
