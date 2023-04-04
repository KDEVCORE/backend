package com.kdevcore.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;
    private final String DEV_URL = "http://localhost:3000";
    private final String PROD_HTTPS_URL = "https://app.kdevcore.com";
    private final String PROD_HTTPS_ROOT_URL = "https://kdevcore.com";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(DEV_URL, PROD_HTTPS_URL, PROD_HTTPS_ROOT_URL)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
