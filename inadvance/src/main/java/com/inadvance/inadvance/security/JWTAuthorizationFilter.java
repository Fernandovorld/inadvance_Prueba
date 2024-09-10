package com.inadvance.inadvance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            logger.info("Extracted Token: {}", bearerToken); // Log the extracted token
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) TokenUtils.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("User authenticated: {}", auth.getName());
            } else {
                logger.error("Token validation failed. Authentication is null.");
            }
        }else {
            logger.warn("No Bearer token found in the request.");
        }
        filterChain.doFilter(request, response);
    }
}
