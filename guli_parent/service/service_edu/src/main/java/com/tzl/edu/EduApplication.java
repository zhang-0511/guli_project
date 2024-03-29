package com.tzl.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.tzl")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.tzl"})
public class EduApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduApplication.class, args);
	}
}
