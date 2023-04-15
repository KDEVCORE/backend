package com.kdevcore.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
    info = @Info(
        title = "KDEVCORE application API",
        description = "List of api rules used in this project",
        version = "0.1.230414",
        contact = @Contact(
                    name = "developer (kdevcore)",
                    email = "kdevcore@gmail.com")
    ),
    tags = {
        @Tag(name = "Member", description = "Member process"),
        @Tag(name = "Todo", description = "Todo process"),
        @Tag(name = "Undefined", description = "Undefined process"),
    }
)
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
