package com.diegoslourenco.users;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Users API",
		version = "1.0",
		description = "API to manage users and profiles",
		contact = @Contact(
				name = "Diego da Silva Lourenço",
				url = "https://www.linkedin.com/in/diegoslourenco",
				email = "diego.lourenco15@gmail.com"
		)
))
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
