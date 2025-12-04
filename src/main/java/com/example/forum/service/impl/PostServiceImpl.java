package com.example.forum.service.impl;

import com.example.forum.entity.Category;
import com.example.forum.entity.Post;
import com.example.forum.entity.Tag;
import com.example.forum.entity.User;
import com.example.forum.model.CreatePostRequest;
import com.example.forum.model.PostDetail;
import com.example.forum.model.PostListItem;
import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.UpdatePostRequest;
import com.example.forum.repository.CategoryRepository;
import com.example.forum.repository.PostRepository;
import com.example.forum.repository.TagRepository;
import com.example.forum.repository.UserRepository;
import com.example.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Transactional
    @Override
    public PostDetail createPost(Long userId, CreatePostRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Category category = categoryRepository.findById(request.getCategoryId().longValue()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(user);
        post.setCategory(category);
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setUpdatedAt(java.time.LocalDateTime.now());
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setCommentCount(0);
        
        // 处理标签
        
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tagList = new ArrayList<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag(tagName);
                        newTag.setCreatedAt(java.time.LocalDateTime.now());
                        return tagRepository.save(newTag);
                    });
                tagList.add(tag);
            }
            post.setTags(tagList);
        }
        
        post = postRepository.save(post);
          return convertToPostDetail(post);
    }
    
    @Transactional(readOnly = true)
    @Override
    public PostDetail getPostDetail(Integer postId) {
        Post postEntity = postRepository.findById(postId.longValue())
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return convertToPostDetail(postEntity);
    }
    
    @Transactional
    @Override
    public PostDetail updatePost(Integer postId, Long userId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId.longValue())
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        // 检查权限
        if (!post.getAuthor().getId().equals(userId)) {
            throw new SecurityException("You don't have permission to edit this post");
        }
        
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId().longValue()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
            post.setCategory(category);
        }
        
        // 处理标签
        if (request.getTags() != null) {
            List<Tag> tagList = new ArrayList<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag(tagName);
                        newTag.setCreatedAt(java.time.LocalDateTime.now());
                        return tagRepository.save(newTag);
                    });
                tagList.add(tag);
            }
            post.setTags(tagList);
        }
        
        post.setUpdatedAt(java.time.LocalDateTime.now());
        post = postRepository.save(post);
        return convertToPostDetail(post);
    }
    
    @Transactional
    @Override
    public void deletePost(Integer postId, Long userId) {
        Post post = postRepository.findById(postId.longValue())
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        // 检查权限
        if (!post.getAuthor().getId().equals(userId)) {
            throw new SecurityException("You don't have permission to delete this post");
        }
        
        postRepository.delete(post);
    }
    
    @Override
    public Page<PostListItem> getPosts(Integer page, Integer limit, String sort, Integer category, String tag) {
        // 确定排序方向
        Sort.Direction direction = Sort.Direction.DESC;
        String sortField = "createdAt"; // 默认按创建时间排序
        
        if (sort != null) {
            switch (sort.toLowerCase()) {
                case "hot":
                    sortField = "viewCount";
                    break;
                case "latest":
                    sortField = "createdAt";
                    break;
                case "popular":
                    sortField = "likeCount";
                    break;
                // 如果是自定义排序字段，直接使用
                default:
                    if (!sort.isEmpty()) {
                        sortField = sort;
                    }
                    break;
            }
        }
        
        // 创建分页请求
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(direction, sortField));
        
        Page<Post> postEntities;
        
        // 根据筛选条件查询帖子
        if (category != null && tag != null) {
            // 按分类和标签筛选
            postEntities = postRepository.findByCategoryIdAndTags_Name(category.longValue(), tag, pageable);
        } else if (category != null) {
            // 按分类筛选
            postEntities = postRepository.findByCategoryId(category.longValue(), pageable);
        } else if (tag != null) {
            // 按标签筛选
            postEntities = postRepository.findByTags_Name(tag, pageable);
        } else {
            // 获取所有帖子
            postEntities = postRepository.findAll(pageable);
        }
        
        return postEntities.map(this::convertToPostListItem);
    }
    
    @Transactional
    @Override
    public void incrementViewCount(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }
    
    private PostDetail convertToPostDetail(Post post) {
        PostDetail detail = new PostDetail();
        detail.setId(Math.toIntExact(post.getId()));
        detail.setTitle(post.getTitle());
        detail.setContent(post.getContent());
        
        // 设置作者信息
        PublicUserProfile author = new PublicUserProfile();
        author.setId(Math.toIntExact(post.getAuthor().getId()));
        author.setUsername(post.getAuthor().getUsername());
        detail.setAuthor(author);
        
        // 设置分类信息
        com.example.forum.model.Category modelCategory = new com.example.forum.model.Category();
        modelCategory.setId(Math.toIntExact(post.getCategory().getId()));
        modelCategory.setName(post.getCategory().getName());
        detail.setCategory(modelCategory);
        
        detail.setViewCount(post.getViewCount());
        detail.setLikeCount(post.getLikeCount());
        detail.setCommentCount(post.getCommentCount());
        
        // 转换日期为OffsetDateTime
        detail.setCreatedAt(convertToOffsetDateTime(post.getCreatedAt()));
        detail.setUpdatedAt(convertToOffsetDateTime(post.getUpdatedAt()));
        
        // 生成摘要
        if (post.getContent() != null && post.getContent().length() > 100) {
            detail.setExcerpt(post.getContent().substring(0, 100) + "...");
        } else {
            detail.setExcerpt(post.getContent());
        }
        
        // 转换标签
        List<com.example.forum.model.Tag> modelTags = new ArrayList<>();
        for (Tag entityTag : post.getTags()) {
            com.example.forum.model.Tag modelTag = new com.example.forum.model.Tag();
            modelTag.setName(entityTag.getName());
            modelTags.add(modelTag);
        }
        detail.setTags(modelTags);
        
        return detail;
    }
    
    private PostListItem convertToPostListItem(Post post) {
        PostListItem item = new PostListItem();
        item.setId(Math.toIntExact(post.getId()));
        item.setTitle(post.getTitle());
        
        // 设置作者信息
        PublicUserProfile author = new PublicUserProfile();
        author.setId(Math.toIntExact(post.getAuthor().getId()));
        author.setUsername(post.getAuthor().getUsername());
        item.setAuthor(author);
        
        // 设置分类信息
        com.example.forum.model.Category modelCategory = new com.example.forum.model.Category();
        modelCategory.setId(Math.toIntExact(post.getCategory().getId()));
        modelCategory.setName(post.getCategory().getName());
        item.setCategory(modelCategory);
        
        item.setViewCount(post.getViewCount());
        item.setLikeCount(post.getLikeCount());
        item.setCommentCount(post.getCommentCount());
        
        // 转换日期为OffsetDateTime
        item.setCreatedAt(convertToOffsetDateTime(post.getCreatedAt()));
        
        // 生成摘要
        if (post.getContent() != null && post.getContent().length() > 100) {
            item.setExcerpt(post.getContent().substring(0, 100) + "...");
        } else {
            item.setExcerpt(post.getContent());
        }
        
        return item;
    }
    
    private java.time.OffsetDateTime convertToOffsetDateTime(java.time.LocalDateTime localDateTime) {
        return localDateTime.atOffset(java.time.ZoneOffset.UTC);
    }
}