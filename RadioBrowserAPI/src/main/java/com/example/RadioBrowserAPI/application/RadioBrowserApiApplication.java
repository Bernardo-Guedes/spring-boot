package com.example.RadioBrowserAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableJpaRepositories(basePackages = "com.example.RadioBrowserAPI.repository")
@EntityScan(basePackages = "com.example.RadioBrowserAPI.entities")
public class RadioBrowserApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RadioBrowserApiApplication.class, args);
	}

}