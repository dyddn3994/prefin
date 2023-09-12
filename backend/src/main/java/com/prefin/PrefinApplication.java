package com.prefin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrefinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrefinApplication.class, args);
	}

}
