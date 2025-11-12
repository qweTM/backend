package com.example.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_likes")
public class CommentLike {
    // 默认构造函数，用于JPA
    public CommentLike() {
    }
    @EmbeddedId
    private CommentLikeId id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        // 延迟初始化id，由JPA处理
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class CommentLikeId implements java.io.Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "comment_id")
        private Long commentId;

        public CommentLikeId(Long userId, Long commentId) {
            this.userId = userId;
            this.commentId = commentId;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommentLikeId that = (CommentLikeId) o;
            return userId.equals(that.userId) && commentId.equals(that.commentId);
        }
        
        @Override
        public int hashCode() {
            return java.util.Objects.hash(userId, commentId);
        }
    }
    
    // Getters and Setters
    public CommentLikeId getId() {
        return id;
    }

    public void setId(CommentLikeId id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}