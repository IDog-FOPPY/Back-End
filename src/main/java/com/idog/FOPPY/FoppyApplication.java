package com.idog.FOPPY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@EnableWebMvc
@SpringBootApplication
public class FoppyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoppyApplication.class, args);
	}

}
