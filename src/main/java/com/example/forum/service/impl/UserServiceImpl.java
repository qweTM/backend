package com.example.forum.service.impl;

import com.example.forum.entity.User;
import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.UpdateProfileRequest;
import com.example.forum.model.UserProfile;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserProfile getCurrentUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        return convertToUserProfile(user);
    }

    @Transactional(readOnly = true)
    @Override
    public PublicUserProfile getPublicUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        return convertToPublicUserProfile(user);
    }

    @Transactional
    @Override
    public UserProfile updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        // 更新用户资料
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        user = userRepository.save(user);
        return convertToUserProfile(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new ArrayList<>());
    }
    
    // 辅助方法
    public User registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setJoinDate(java.time.LocalDateTime.now());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return userRepository.save(user);
    }
    
    public Optional<User> loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElse(null);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    
    @Transactional(readOnly = true)
    @Override
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        return user.getId();
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    private UserProfile convertToUserProfile(User user) {
        UserProfile profile = new UserProfile();
        profile.setId(user.getId().intValue());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        profile.setAvatar(user.getAvatar());
        profile.setBio(user.getBio());
        profile.setJoinDate(user.getJoinDate().atOffset(java.time.ZoneOffset.UTC));
        // 正确计算帖子数量
        profile.setPostCount(user.getPosts() != null ? user.getPosts().size() : 0);
        // 正确计算评论数量
        profile.setCommentCount(user.getComments() != null ? user.getComments().size() : 0);
        return profile;
    }
    
    private PublicUserProfile convertToPublicUserProfile(User user) {
        PublicUserProfile profile = new PublicUserProfile();
        profile.setId(user.getId().intValue());
        profile.setUsername(user.getUsername());
        profile.setAvatar(user.getAvatar());
        profile.setBio(user.getBio());
        return profile;
    }
    
    public String generateToken(User user) {
        // 生成简单的token，实际项目中应使用JWT
        return UUID.randomUUID().toString();
    }
    
    public User validateToken(String token) {
        // 验证token，实际项目中应解析JWT
        // 由于是演示，暂时返回第一个用户
        return userRepository.findAll().get(0);
    }
}