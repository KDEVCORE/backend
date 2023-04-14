package com.kdevcore.backend.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
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
    },
    security = {
        @SecurityRequirement(name = "X-Auth-Token"),
    }
)
@SecuritySchemes({
    @SecurityScheme(name = "X-Auth-Token",
                    type = SecuritySchemeType.APIKEY,
                    description = "API token",
                    in = SecuritySchemeIn.HEADER,
                    paramName = "X-Auth-Token"),
})
@Configuration
public class SpringDocConfig {
}
