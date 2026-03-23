package com.astraflow.astraflow_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astraflow.astraflow_backend.dto.ErrorResponse;
import com.astraflow.astraflow_backend.dto.LoginRequest;
import com.astraflow.astraflow_backend.dto.LoginResponse;
import com.astraflow.astraflow_backend.dto.RegisterRequest;
import com.astraflow.astraflow_backend.dto.RegisterResponse;
import com.astraflow.astraflow_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    /**
     * User registration endpoint
     * POST /api/auth/register
     * Body: {"email": "rama@test.com", "password": "test123", "name": "Rama"}
     * Returns: {"userId": 1, "email": "rama@test.com", "message": "..."}
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);
            return ResponseEntity.status(201).body(response);  // 201 Created
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Registration failed", e.getMessage())
            );
        }
    }
    
    /**
     * User login endpoint
     * POST /api/auth/login
     * Body: {"email": "rama@test.com", "password": "test123"}
     * Returns: {"token": "eyJ...", "userId": 1, "email": "rama@test.com"}
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);  // 200 OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Login failed", e.getMessage())
            );
        }
    }
}