package com.example.forum.service;

import com.example.forum.model.Comment;
import com.example.forum.model.CreateCommentRequest;
import com.example.forum.model.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, Long userId, CreateCommentRequest request);
    List<Comment> getCommentsByPost(Long postId, Integer page, Integer limit);
    Comment updateComment(Long commentId, Long userId, UpdateCommentRequest request);
    void deleteComment(Long commentId, Long userId);
    // 新增方法：根据ID获取评论
    Comment getCommentById(Long commentId);
}