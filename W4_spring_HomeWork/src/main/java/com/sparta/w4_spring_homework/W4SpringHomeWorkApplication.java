package com.sparta.w4_spring_homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class W4SpringHomeWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(W4SpringHomeWorkApplication.class, args);
	}

}
