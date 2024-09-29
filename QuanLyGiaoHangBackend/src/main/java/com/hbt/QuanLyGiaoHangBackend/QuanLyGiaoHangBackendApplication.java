package com.hbt.QuanLyGiaoHangBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuanLyGiaoHangBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyGiaoHangBackendApplication.class, args);
	}

}
