package com.example.forum.service.impl;

import com.example.forum.entity.Comment;
import com.example.forum.entity.CommentLike;
import com.example.forum.entity.User;
import com.example.forum.repository.CommentLikeRepository;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void likeComment(Long userId, Long commentId) {
        // 查找用户和评论
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        
        // 检查是否已经点赞
        if (commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw new IllegalArgumentException("You have already liked this comment");
        }
        
        // 创建新的点赞记录
        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
    }
    
    @Override
    public void unlikeComment(Long userId, Long commentId) {
        // 查找用户和评论
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        
        Optional<CommentLike> commentLike = commentLikeRepository.findByUserAndComment(user, comment);
        if (commentLike.isPresent()) {
            commentLikeRepository.delete(commentLike.get());
        } else {
            throw new IllegalArgumentException("You have not liked this comment");
        }
    }
    
    @Override
    public boolean isCommentLikedByUser(Long userId, Long commentId) {
        // 查找用户和评论
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        
        return commentLikeRepository.existsByUserAndComment(user, comment);
    }

    @Override
    public Long getCommentLikeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}