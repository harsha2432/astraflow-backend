package com.astraflow.astraflow_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/birthchart")
@CrossOrigin(origins = "http://localhost:4200")
public class BirthChartController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AstraFlow API is running!");
    }
}