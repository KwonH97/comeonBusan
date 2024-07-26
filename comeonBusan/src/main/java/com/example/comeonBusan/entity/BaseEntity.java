package com.example.comeonBusan.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

	@Column(name = "regdate", updatable = false, nullable = false)
	private Date regdate;

	@Column(name = "moddate")
	private Date moddate;

	@PrePersist
	protected void onCreate() {

		regdate = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		moddate = new Date();
	}
}
