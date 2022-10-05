package com.example.obspringsecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ObSpringSecurityJwtApplication {

	public static void main(String[] args) {
		// Contenedor de beans
				SpringApplication.run(ObSpringSecurityJwtApplication.class, args);



	}

}
