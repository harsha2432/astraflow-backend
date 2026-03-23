package com.astraflow.astraflow_backend.service;

import com.astraflow.astraflow_backend.model.User;
import com.astraflow.astraflow_backend.repository.UserRepository;
import com.astraflow.astraflow_backend.dto.RegisterRequest;
import com.astraflow.astraflow_backend.dto.RegisterResponse;
import com.astraflow.astraflow_backend.dto.LoginRequest;
import com.astraflow.astraflow_backend.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Register new user
     * 1. Validate email is unique
     * 2. Hash password with BCrypt
     * 3. Save to database
     * 4. Return response
     */
    public RegisterResponse register(RegisterRequest request) {
        
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        
        // Hash password before saving (critical!)
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);
        
        // Save to database
        User savedUser = userRepository.save(user);
        
        return new RegisterResponse(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getName(),
            "User registered successfully"
        );
    }
    
    /**
     * Login user with email and password
     * @param request Contains email and password
     * @return LoginResponse with JWT token
     */
    public LoginResponse login(LoginRequest request) {
        
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // 2. Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        // 3. Generate JWT token
        String token = jwtService.generateToken(user.getId(), user.getEmail());
        
        // 4. Return token to client
        return new LoginResponse(token, user.getId(), user.getEmail());
    }
}