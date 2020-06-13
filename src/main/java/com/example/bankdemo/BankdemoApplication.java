package com.example.bankdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.bankdemo.dao")
@EntityScan("com.example.bankdemo.data")
public class BankdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankdemoApplication.class, args);
	}

}
