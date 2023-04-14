package com.kdevcore.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @Tag(name = "Undefined")
@RestController
public class HealthCheckController {
    // @Operation(summary = "check running server", description = "서버가 실행중인지 확인하는 용도")
    @GetMapping("/health")
    public String healthCheck() {
        return "The service is up and running...";
    }
}
