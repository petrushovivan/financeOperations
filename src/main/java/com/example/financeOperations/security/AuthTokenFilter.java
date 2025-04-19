package com.example.financeOperations.security;

import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;

    @Autowired
    public AuthTokenFilter(JWTUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String jwt = jwtUtils.getToken(request);

        if (jwt == null || !jwtUtils.validateJWT(jwt)) {
            sendError(response, "invalid token");
            return;
        }

        String email = jwtUtils.getEmailFromJWT(jwt);

        if (email == null) {
            sendError(response, "invalid token");
            return;
        }
        User user = userRepository.findUserByEmail(email);

        if (user == null || !user.getToken().equals(jwt)) {
            sendError(response, "invalid jwt");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendError(HttpServletResponse httpServletResponse, String message) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> body = new HashMap<>();
        body.put("status", false);
        body.put("message", message);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), body);
    }
}