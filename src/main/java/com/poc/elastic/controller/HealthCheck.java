package com.poc.elastic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthCheck {

    @GetMapping("/check")
    public Map<String, String> healthCheck() {
        return Collections.singletonMap("elastic-search", "active");
    }

}
