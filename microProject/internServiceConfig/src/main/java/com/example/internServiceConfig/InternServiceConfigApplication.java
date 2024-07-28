package com.example.internServiceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class InternServiceConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternServiceConfigApplication.class, args);
	}

}
