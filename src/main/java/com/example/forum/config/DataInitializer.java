package com.example.forum.config;

import com.example.forum.entity.Category;
import com.example.forum.entity.User;
import com.example.forum.repository.CategoryRepository;
import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化默认用户
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            // 如果User实体有nickname字段，可以取消下面注释
            // adminUser.setNickname("管理员");
            adminUser.setJoinDate(java.time.LocalDateTime.now());
            adminUser.setCreatedAt(java.time.LocalDateTime.now());
            adminUser.setUpdatedAt(java.time.LocalDateTime.now());
            userRepository.save(adminUser);
        }
        
        // 初始化默认分类
        if (categoryRepository.count() == 0) {
            String[] categoryNames = {"技术讨论", "经验分享", "问题求助", "资源推荐", "灌水闲聊"};
            String[] categoryDescriptions = {
                "讨论技术相关话题",
                "分享工作和学习经验",
                "遇到问题来求助",
                "推荐优质学习资源",
                "轻松闲聊的版块"
            };
            String[] categoryColors = {"#1890ff", "#52c41a", "#f5222d", "#722ed1", "#faad14"};
            
            for (int i = 0; i < categoryNames.length; i++) {
                Category category = new Category();
                category.setName(categoryNames[i]);
                category.setDescription(categoryDescriptions[i]);
                category.setColor(categoryColors[i]);
                category.setCreatedAt(java.time.LocalDateTime.now());
                category.setUpdatedAt(java.time.LocalDateTime.now());
                categoryRepository.save(category);
            }
        }
    }
}