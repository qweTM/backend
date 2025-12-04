package com.example.forum;

import com.example.forum.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenGeneratorTest {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    public void testGenerateToken() {
        String username = "testuser";
        String token = jwtTokenService.generateToken(username);
        System.out.println("Generated Token for user '" + username + "':");
        System.out.println(token);
        System.out.println("\nToken validation: " + jwtTokenService.validateToken(token));
        System.out.println("Username from token: " + jwtTokenService.getUsernameFromToken(token));
    }
}