package com.example.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
@Data
@NoArgsConstructor
public class PostLike {
    @EmbeddedId
    private PostLikeId id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
        // 延迟初始化id，由JPA处理
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class PostLikeId implements java.io.Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "post_id")
        private Long postId;

        public PostLikeId(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }
}