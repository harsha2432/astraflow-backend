package com.astraflow.astraflow_backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    
    @Value("${jwt.secret:AstraFlowSecretKeyThatIsAtLeast256BitsLongForHS256Algorithm}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")  // 24 hours in milliseconds
    private long jwtExpirationInMs;
    
    /**
     * Generate JWT token for user
     */
    public String generateToken(Long userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("email", email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
    
    /**
     * Extract userId from token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        
        return Long.parseLong(claims.getSubject());
    }
    
    /**
     * Validate token and return Claims
     * ✅ FIXED: Returns Claims object instead of boolean
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();  // ✅ Return Claims object
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token: " + e.getMessage());
        }
    }

    public Long extractUserId(String token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}