package com.jobms.Job_Micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobMicroApplication.class, args);
	}

}
