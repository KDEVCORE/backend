package com.kdevcore.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health Check", description = "A controller that checks server status")
@RestController
public class HealthCheckController {
    @Operation(summary = "Check running server", description = "서버가 실행중인지 확인하는 용도")
    @GetMapping("/health")
    public String healthCheck() {
        return "The service is up and running...";
    }
}
