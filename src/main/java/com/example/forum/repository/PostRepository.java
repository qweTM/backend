package com.example.forum.repository;

import com.example.forum.entity.Category;
import com.example.forum.entity.Post;
import com.example.forum.entity.Tag;
import com.example.forum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Post> findByTags_Name(String tagName, Pageable pageable);
    Page<Post> findByCategoryIdAndTags_Name(Long categoryId, String tagName, Pageable pageable);
    Page<Post> findByTagsIn(List<Tag> tags, Pageable pageable);
    Page<Post> findByAuthor(User author, Pageable pageable);
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    List<Post> findTop10ByOrderByLikeCountDesc();
}