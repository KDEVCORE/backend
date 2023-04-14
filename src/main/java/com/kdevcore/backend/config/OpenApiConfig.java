package com.kdevcore.backend.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
    info = @Info(
        title = "KDEVCORE application API",
        description = "List of api rules used in this project",
        version = "${springdoc.swagger-ui.version}",
        contact = @Contact(
                    name = "kdevcore",
                    email = "kdevcore@gmail.com")
    ),
    tags = {
        @Tag(name = "Member", description = "Member process"),
        @Tag(name = "Todo", description = "Todo process"),
        @Tag(name = "Undefined", description = "Undefined process"),
    }
)
@Configuration
public class OpenApiConfig {
}
