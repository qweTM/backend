package com.example.forum.service;

import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.UpdateProfileRequest;
import com.example.forum.model.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    UserProfile getCurrentUserProfile(Long userId);
    
    PublicUserProfile getPublicUserProfile(Long userId);
    
    UserProfile updateProfile(Long userId, UpdateProfileRequest updateProfileRequest);
}