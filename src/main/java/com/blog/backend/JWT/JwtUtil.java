package com.blog.backend.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Secret key (keep it safe!)
    private final String SECRET_KEY = "8f2c4e9b7a1d3f6e5b0c9a4d8e7f2b6a9c1d4e5f8a7b3c2e9d6f1a0b5c4";

    // Method to generate token (getToken)
    public String getToken(String username) {
        // Token validity in milliseconds (e.g., 10 hours)
        long TOKEN_VALIDITY = 10 * 60 * 60 * 1000;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Method to extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Private helper to extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
