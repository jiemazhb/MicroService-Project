package com.example.interServiceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class InterServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterServiceRegistryApplication.class, args);
	}

}
