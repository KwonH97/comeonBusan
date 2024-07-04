package com.example.comeonBusan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Help {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hnum;
	
	private String title;
	private String content;
	
	public void update(Help help) {
		this.title = help.getTitle();
		this.content = help.getContent();
	}
}
