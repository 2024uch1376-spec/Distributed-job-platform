package com.configserver.EnableConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EnableConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnableConfigServerApplication.class, args);
	}

}
