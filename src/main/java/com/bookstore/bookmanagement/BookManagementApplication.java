package com.bookstore.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}

}
