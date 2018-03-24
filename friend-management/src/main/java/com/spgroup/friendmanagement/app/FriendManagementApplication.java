package com.spgroup.friendmanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories("com.spgroup.friendmanagement.dao.repository")
@EntityScan("com.spgroup.friendmanagement.dto")
@ComponentScan("com.spgroup.friendmanagement")
@EnableWebMvc
@EnableAutoConfiguration
public class FriendManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendManagementApplication.class, args);
	}
}
