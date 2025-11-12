package com.example.forum.service.impl;

import com.example.forum.entity.Comment;
import com.example.forum.entity.Post;
import com.example.forum.entity.User;
import com.example.forum.model.CreateCommentRequest;
import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.UpdateCommentRequest;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.PostRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    @Override
    public com.example.forum.model.Comment createComment(Long postId, Long userId, CreateCommentRequest request) {
        // 查找帖子
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("帖子不存在"));

        // 查找用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        // 创建评论
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setAuthor(user);
        comment.setPost(post);

        // 处理回复
        if (request.getParentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentId().longValue())
                    .orElseThrow(() -> new IllegalArgumentException("父评论不存在"));
            // 检查父评论是否属于同一帖子
            if (!parentComment.getPost().getId().equals(postId)) {
                throw new IllegalArgumentException("父评论不属于该帖子");
            }
            comment.setParent(parentComment);
        }

        // 保存评论
        comment = commentRepository.save(comment);

        // 更新帖子评论数
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return convertToModel(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<com.example.forum.model.Comment> getCommentsByPost(Long postId, Integer page, Integer limit) {
        // 查找帖子
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("帖子不存在"));

        // 设置分页
        PageRequest pageable = PageRequest.of(
                page != null ? page - 1 : 0, 
                limit != null ? limit : 20
        );

        // 查询评论（只获取顶级评论）
        Page<Comment> commentPage = commentRepository.findByPostAndParentIsNull(post, pageable);

        // 转换为响应模型
        return commentPage.getContent().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public com.example.forum.model.Comment updateComment(Long commentId, Long userId, UpdateCommentRequest request) {
        // 查找评论
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));

        // 检查是否是评论作者
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("无权限修改此评论");
        }

        // 更新评论内容
        comment.setContent(request.getContent());

        // 保存更新
        comment = commentRepository.save(comment);

        return convertToModel(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, Long userId) {
        // 查找评论
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));

        // 检查是否是评论作者或帖子作者
        boolean isCommentAuthor = comment.getAuthor().getId().equals(userId);
        boolean isPostAuthor = comment.getPost().getAuthor().getId().equals(userId);

        if (!isCommentAuthor && !isPostAuthor) {
            throw new IllegalArgumentException("无权限删除此评论");
        }

        // 获取帖子，用于更新评论数
        Post post = comment.getPost();

        // 删除评论
        commentRepository.delete(comment);

        // 更新帖子评论数
        post.setCommentCount(post.getCommentCount() - 1);
        postRepository.save(post);
    }

    // 转换为响应模型
    private com.example.forum.model.Comment convertToModel(com.example.forum.entity.Comment commentEntity) {
        com.example.forum.model.Comment comment = new com.example.forum.model.Comment();
        comment.setId(commentEntity.getId().intValue());
        comment.setContent(commentEntity.getContent());
        
        // 创建PublicUserProfile对象
        PublicUserProfile authorProfile = new PublicUserProfile();
        authorProfile.setUsername(commentEntity.getAuthor().getUsername());
        comment.setAuthor(authorProfile);
        
        // 设置postId
        comment.setPostId(commentEntity.getPost().getId().intValue());
        
        // 设置parentId
        if (commentEntity.getParent() != null) {
            comment.setParentId(commentEntity.getParent().getId().intValue());
        }
        
        // 转换日期时间
        comment.setCreatedAt(convertToOffsetDateTime(commentEntity.getCreatedAt()));
        comment.setUpdatedAt(convertToOffsetDateTime(commentEntity.getUpdatedAt()));
        comment.setLikeCount(commentEntity.getLikeCount());

        // 设置回复
        if (!commentEntity.getReplies().isEmpty()) {
            List<com.example.forum.model.Comment> replies = commentEntity.getReplies().stream()
                    .map(this::convertToModel)
                    .collect(Collectors.toList());
            comment.setReplies(replies);
        }

        return comment;
    }
    
    // 辅助方法：将LocalDateTime转换为OffsetDateTime
    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(java.time.ZoneOffset.UTC);
    }
    
    // 转换为实体
    private com.example.forum.entity.Comment convertToEntity(com.example.forum.model.Comment commentModel) {
        com.example.forum.entity.Comment comment = new com.example.forum.entity.Comment();
        if (commentModel.getId() != null) {
            comment.setId(commentModel.getId().longValue());
        }
        comment.setContent(commentModel.getContent());
        
        // 作者信息通常在转换时从外部传入，这里不直接从commentModel获取
        
        // 设置parent
        if (commentModel.getParentId() != null) {
            com.example.forum.entity.Comment parentComment = new com.example.forum.entity.Comment();
            parentComment.setId(commentModel.getParentId().longValue());
            comment.setParent(parentComment);
        }
        
        // 转换OffsetDateTime到LocalDateTime
        if (commentModel.getCreatedAt() != null) {
            comment.setCreatedAt(commentModel.getCreatedAt().toLocalDateTime());
        }
        if (commentModel.getUpdatedAt() != null) {
            comment.setUpdatedAt(commentModel.getUpdatedAt().toLocalDateTime());
        }
        
        return comment;
    }
}