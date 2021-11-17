package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/update")
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @GetMapping("/token")
    protected void updateToken(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        String default_token = jwtProvider.createToken(authResult);
        String refresh_token = jwtProvider.createRefreshToken(authResult);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", default_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}
