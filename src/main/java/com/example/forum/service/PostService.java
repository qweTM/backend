package com.example.forum.service;

import com.example.forum.model.CreatePostRequest;
import com.example.forum.model.PostDetail;
import com.example.forum.model.PostListItem;
import com.example.forum.model.UpdatePostRequest;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PostService {
    
    PostDetail createPost(Long userId, CreatePostRequest request);
    
    PostDetail getPostDetail(Integer postId);
    
    PostDetail updatePost(Integer postId, Long userId, UpdatePostRequest request);
    
    void deletePost(Integer postId, Long userId);
    
    org.springframework.data.domain.Page<PostListItem> getPosts(Integer page, Integer limit, String sort, Integer category, String tag);
    
    void incrementViewCount(Long postId);
}