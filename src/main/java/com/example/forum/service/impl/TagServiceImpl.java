package com.example.forum.service.impl;

import com.example.forum.entity.Tag;
import com.example.forum.repository.TagRepository;
import com.example.forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    
    @Transactional(readOnly = true)
    @Override
    public List<com.example.forum.model.Tag> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<com.example.forum.model.Tag> getPopularTags(Integer limit) {
        // 获取所有标签，并按使用频率排序
        List<Tag> tags = tagRepository.findAll();
        // 按关联的帖子数量降序排序
        tags.sort((t1, t2) -> Integer.compare(t2.getPosts().size(), t1.getPosts().size()));
        
        // 使用提供的limit参数或默认值20
        int actualLimit = (limit != null && limit > 0) ? limit : 20;
        
        return tags.stream()
                .limit(actualLimit)
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }
    
    // 转换为响应模型
    private com.example.forum.model.Tag convertToModel(Tag tagEntity) {
        com.example.forum.model.Tag tag = new com.example.forum.model.Tag();
        tag.setId(Math.toIntExact(tagEntity.getId()));
        tag.setName(tagEntity.getName());
        tag.setPostCount(tagEntity.getPosts().size());
        return tag;
    }
}