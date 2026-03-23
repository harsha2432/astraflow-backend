package com.astraflow.astraflow_backend.dto;

public class RegisterResponse {
    private Long userId;
    private String email;
    private String name;
    private String message;
    
    public RegisterResponse(Long userId, String email, String name, String message) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.message = message;
    }
    
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getMessage() { return message; }
}