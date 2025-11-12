package com.example.forum.service;

import com.example.forum.entity.Category;
import com.example.forum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<com.example.forum.model.Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    // 转换为响应模型
    private com.example.forum.model.Category convertToModel(Category categoryEntity) {
        com.example.forum.model.Category category = new com.example.forum.model.Category();
        category.setId(categoryEntity.getId().intValue());
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        category.setColor(categoryEntity.getColor());
        // 使用post.size()作为postCount
        category.setPostCount(categoryEntity.getPosts().size());
        return category;
    }
}