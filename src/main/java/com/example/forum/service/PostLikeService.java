package com.example.forum.service;

public interface PostLikeService {
    void likePost(Long userId, Long postId);
    void unlikePost(Long userId, Long postId);
    boolean isPostLikedByUser(Long userId, Long postId);
    Long getPostLikeCount(Long postId);
}