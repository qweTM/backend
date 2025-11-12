package com.example.forum.service;

import com.example.forum.model.Tag;

import java.util.List;

public interface TagService {
    
    List<Tag> getAllTags();
    
    List<Tag> getPopularTags();
}