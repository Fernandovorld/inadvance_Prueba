package com.inadvance.inadvance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TokenUtils {

    private static final String SECRET_KEY = "mySecretKey12345678901234567890123456789012"; // 32 bytes
    private static final Long EXPIRATION_TIME = 3600000L; // 1 hour

    public static String generateToken(Authentication auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", auth.getName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public static Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
    }
}
