package com.alpha.authservice.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key key = Keys.hmacShaKeyFor(
            "mySecretKeyForRideBookingApplication123456789".getBytes()
    );

    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }
    
    
    public String testToken() {
        return generateToken("Manoj");
    }
    
}