package com.example.forum.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {
    public static void main(String[] args) {
        // 验证输入参数
        if (args.length != 1) {
            System.err.println("使用方法: java -cp target/classes com.example.forum.util.TokenGenerator <username>");
            System.err.println("或在IDE中运行，输入用户名作为程序参数");
            System.exit(1);
        }
        String username = args[0];
        System.out.println("为用户生成令牌: " + username);
        System.out.println("生成的JWT令牌: " + generateToken(username));
    }

    public static String generateToken(String username) {
        // 与项目JwtTokenService中相同的配置
        String jwtSecret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6InRlc3R1c2VyMTIzIiwiZXhwIjoxNzYzMDYxNTAwLCJpYXQiOjE3NjI5NzUxMDB9.n3bFSzJjA0T0xQkfzCH-5b6iEt2KgG91ptuEy9FYRFSWSvZzq4gj_FHQmQy4vWIcGYQ-NDEy3vLnaheXFArwow";
        long jwtExpirationMs = 86400000; // 1天

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}