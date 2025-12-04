package com.example.forum.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService {

    // 默认配置，用于命令行生成器
    private static final String DEFAULT_JWT_SECRET = "forum_secret_key_change_this_in_production";
    private static final long DEFAULT_JWT_EXPIRATION_MS = 86400000;
    
    @Value("${jwt.secret:forum_secret_key_change_this_in_production}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // 命令行工具：直接运行生成令牌
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("使用方法: java -cp target/classes:target/dependency/* com.example.forum.service.JwtTokenService <username>");
            System.err.println("或: mvn exec:java -Dexec.mainClass=com.example.forum.service.JwtTokenService -Dexec.args='<username>'");
            System.exit(1);
        }
        
        String username = args[0];
        
        // 创建服务实例
        JwtTokenService jwtTokenService = new JwtTokenService();
        
        // 生成令牌
        String token = jwtTokenService.generateToken(username);
        
        System.out.println("为用户生成令牌: " + username);
        System.out.println("生成的JWT令牌: ");
        System.out.println(token);
        System.out.println("\n验证令牌: " + jwtTokenService.validateToken(token));
        System.out.println("从令牌获取用户名: " + jwtTokenService.getUsernameFromToken(token));
        System.out.println("\n注意: 这是一个演示令牌，实际应用中应使用安全的密钥管理");
    }
}