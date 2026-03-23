package com.astraflow.astraflow_backend.controller;

import com.astraflow.astraflow_backend.dto.ChartResponse;
import com.astraflow.astraflow_backend.dto.SaveChartRequest;
import com.astraflow.astraflow_backend.service.ChartService;
import com.astraflow.astraflow_backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charts")
@CrossOrigin(origins = "http://localhost:4200")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private JwtService jwtService;

    /**
     * Save a birth chart for the authenticated user.
     * POST /api/charts/save
     * Header: Authorization: Bearer <JWT>
     * Body: { birthDate, birthCity, birthCountry, chartResult, label }
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveChart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody SaveChartRequest request) {
        try {
            Long userId = extractUserId(authHeader);
            ChartResponse response = chartService.saveChart(userId, request);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse(400, e.getMessage())
            );
        }
    }

    /**
     * Get all charts for the authenticated user.
     * GET /api/charts/my-charts
     * Header: Authorization: Bearer <JWT>
     */
    @GetMapping("/my-charts")
    public ResponseEntity<?> getMyCharts(
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = extractUserId(authHeader);
            List<ChartResponse> charts = chartService.getUserCharts(userId);
            return ResponseEntity.ok(charts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse(400, e.getMessage())
            );
        }
    }

    /**
     * Delete a chart (only if it belongs to the authenticated user).
     * DELETE /api/charts/{id}
     * Header: Authorization: Bearer <JWT>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChart(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = extractUserId(authHeader);
            chartService.deleteChart(id, userId);
            return ResponseEntity.ok("Chart deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse(400, e.getMessage())
            );
        }
    }

    /**
     * Helper: extract userId from "Bearer <token>" header.
     * Throws exception if token is missing or invalid.
     */
    private Long extractUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);  // Remove "Bearer " prefix
        Long userId = jwtService.extractUserId(token);
        if (userId == null) {
            throw new RuntimeException("Invalid or expired token");
        }
        return userId;
    }
}

// Reuse ErrorResponse from AuthController (or move to a shared package)
class ErrorResponse {
    public int status;
    public String message;
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
}