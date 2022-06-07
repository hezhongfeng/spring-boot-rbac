package com.example.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RbacApplication {

	public static void main(String[] args) {
		System.out.println("Hello RBAC!");
		SpringApplication.run(RbacApplication.class, args);
	}

}
