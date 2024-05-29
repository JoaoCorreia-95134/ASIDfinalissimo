package com.iStudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IStudentApplication.class, args);
	}

}
