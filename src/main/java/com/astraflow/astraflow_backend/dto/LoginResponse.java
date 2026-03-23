package com.astraflow.astraflow_backend.dto;

public class LoginResponse {
    
    private String token;
    private Long userId;
    private String email;
    private String message;
    
    public LoginResponse(String token, Long userId, String email) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.message = "Login successful";
    }
    
    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
}