package com.todo1.BackEndTodo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration
public class BackEndTodo1Application {

	public static void main(String[] args) {
		SpringApplication.run(BackEndTodo1Application.class, args);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                		//.allowedOrigins("http://localhost:4200","http://localhost")
                		.allowedOrigins("*")
                		.allowedMethods("GET","POST","PUT","DELETE")
                		.maxAge(36000);
            }
        };
    }
}
