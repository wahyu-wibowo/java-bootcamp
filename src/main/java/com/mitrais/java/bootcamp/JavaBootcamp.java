package com.mitrais.java.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Import(SpringConfig.class)
@EnableAutoConfiguration
@SpringBootApplication
public class JavaBootcamp {

	public static void main(String[] args) {
		SpringApplication.run(JavaBootcamp.class, args);
	}
}
