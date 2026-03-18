package com.astraflow.astraflow_backend.controller;

import com.astraflow.astraflow_backend.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/birthchart")
@CrossOrigin(origins = "http://localhost:4200")
public class BirthChartController {

    private final GeminiService geminiService;

    public BirthChartController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AstraFlow API is running!");
    }

    @GetMapping("/test-ai")
    public ResponseEntity<String> testAi() {
        String narrative = geminiService.generateBirthChartNarrative(
                "Sarah",
                "Scorpio",
                "Pisces",
                "Libra",
                "Pluto",
                "Venus Trine Jupiter"
        );
        return ResponseEntity.ok(narrative);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateChart(
            @RequestBody Map<String, String> request) {

        String userName       = request.getOrDefault("userName", "Friend");
        String sunSign        = request.getOrDefault("sunSign", "Scorpio");
        String moonSign       = request.getOrDefault("moonSign", "Pisces");
        String risingSign     = request.getOrDefault("risingSign", "Libra");
        String dominantPlanet = request.getOrDefault("dominantPlanet", "Pluto");
        String signatureAspect = request.getOrDefault("signatureAspect",
                                                        "Venus Trine Jupiter");

        String narrative = geminiService.generateBirthChartNarrative(
                userName, sunSign, moonSign,
                risingSign, dominantPlanet, signatureAspect
        );

        return ResponseEntity.ok(narrative);
    }
}