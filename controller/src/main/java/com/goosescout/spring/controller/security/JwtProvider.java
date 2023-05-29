package com.goosescout.spring.controller.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDate;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-days}")
    private int jwtExpirationDays;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = now.plusDays(jwtExpirationDays);

        return Jwts.builder()
                .setIssuedAt(java.sql.Date.valueOf(now))
                .setExpiration(java.sql.Date.valueOf(expirationDate))
                .addClaims(Map.ofEntries(
                        Map.entry("username", username),
                        Map.entry("roles", authentication.getAuthorities()
                                .stream()
                                .map(role -> role.toString().substring(5))
                                .toArray())
                ))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
