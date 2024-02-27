package com.pie.pieProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PieProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PieProjectApplication.class, args);
	}

}
