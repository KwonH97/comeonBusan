package com.example.comeonBusan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComeonBusanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComeonBusanApplication.class, args);
	}

}
