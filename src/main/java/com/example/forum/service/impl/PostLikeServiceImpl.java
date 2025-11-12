package com.example.forum.service.impl;

import com.example.forum.entity.Post;
import com.example.forum.entity.PostLike;
import com.example.forum.entity.User;
import com.example.forum.repository.PostLikeRepository;
import com.example.forum.repository.PostRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public void likePost(Long userId, Long postId) {
        // 验证帖子是否存在
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("帖子不存在"));
        
        // 验证用户是否存在
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        
        // 检查是否已经点赞
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalArgumentException("已经点赞过该帖子");
        }
        
        // 创建点赞记录
        PostLike postLike = new PostLike(user, post);
        postLikeRepository.save(postLike);
        
        // 更新帖子点赞数
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }
    
    @Override
    @Transactional
    public void unlikePost(Long userId, Long postId) {
        // 验证帖子是否存在
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("帖子不存在"));
        
        // 验证用户是否存在
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        
        // 检查是否已经点赞
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
            .orElseThrow(() -> new IllegalArgumentException("尚未点赞该帖子"));
        
        // 删除点赞记录
        postLikeRepository.delete(postLike);
        
        // 更新帖子点赞数
        if (post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        }
    }
    
    @Override
    public boolean isPostLikedByUser(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        if (user == null || post == null) {
            return false;
        }
        return postLikeRepository.existsByUserAndPost(user, post);
    }
    
    @Override
    public Long getPostLikeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}