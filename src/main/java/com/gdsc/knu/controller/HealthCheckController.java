package com.gdsc.knu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    // swagger doc

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy!";
    }
}
