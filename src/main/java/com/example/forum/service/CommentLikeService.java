package com.example.forum.service;

public interface CommentLikeService {
    
    void likeComment(Long userId, Long commentId);
    
    void unlikeComment(Long userId, Long commentId);
    
    boolean isCommentLikedByUser(Long userId, Long commentId);
    
    Long getCommentLikeCount(Long commentId);
}