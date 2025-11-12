package com.example.forum.repository;

import com.example.forum.entity.Comment;
import com.example.forum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostAndParentIsNull(Post post, Pageable pageable);
    List<Comment> findByParentId(Long parentId);
    List<Comment> findByAuthorId(Long authorId);
    void deleteByPostId(Long postId);
}