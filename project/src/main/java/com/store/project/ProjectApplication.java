package com.store.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com/store/project")
@EnableAutoConfiguration
@RestController
public class ProjectApplication {

@GetMapping("/message")
public String message(){
	return "Congrats!";
}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}


}
