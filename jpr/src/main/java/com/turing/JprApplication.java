package com.turing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.turing.mapper")
@SpringBootApplication
public class JprApplication {

	public static void main(String[] args) {
		SpringApplication.run(JprApplication.class, args);
	}

}
