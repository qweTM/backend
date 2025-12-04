package com.example.forum.service;

import com.example.forum.entity.User;
import com.example.forum.model.AuthResponse;
import com.example.forum.model.LoginRequest;
import com.example.forum.model.RegisterRequest;
import com.example.forum.model.UserProfile;
import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("邮箱已被注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setJoinDate(java.time.LocalDateTime.now());

        // 保存用户
        user = userRepository.save(user);

        // 生成JWT token
        String token = jwtTokenService.generateToken(user.getUsername());

        // 返回认证响应
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId().intValue());
        userProfile.setUsername(user.getUsername());
        userProfile.setEmail(user.getEmail());
        userProfile.setJoinDate(user.getJoinDate().atOffset(java.time.ZoneOffset.UTC));
        response.setUser(userProfile);

        return response;
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // 验证用户凭据 - 支持通过用户名或邮箱登录
        User user = userRepository.findByUsername(request.getLogin())
                .orElseGet(() -> userRepository.findByEmail(request.getLogin())
                        .orElseThrow(() -> new IllegalArgumentException("用户名或密码错误")));

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 生成JWT token
        String token = jwtTokenService.generateToken(user.getUsername());

        // 返回认证响应
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId().intValue());
        userProfile.setUsername(user.getUsername());
        userProfile.setEmail(user.getEmail());
        userProfile.setJoinDate(user.getJoinDate().atOffset(java.time.ZoneOffset.UTC));
        response.setUser(userProfile);

        return response;
    }

    public void logout(String token) {
        // 在实际项目中，这里可以实现token失效逻辑
        // 例如将token加入黑名单
    }
}