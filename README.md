
# 论坛系统 - Spring Boot + OpenAPI Generator

## 项目概述

本项目是一个基于Spring Boot的论坛系统后端API，使用OpenAPI Generator从openapi.yaml规范自动生成了API接口和数据模型代码。

## 已成功生成的代码结构

```
src/main/java/com/example/forum/
├── ForumApplication.java      # Spring Boot应用主类
├── api/
│   ├── ApiUtil.java           # API工具类
│   └── DefaultApi.java        # 自动生成的API接口（包含所有论坛功能端点）
├── config/
│   └── OpenApiConfig.java     # OpenAPI配置类
└── model/
    ├── AuthResponse.java      # 认证响应模型
    ├── Category.java          # 版块分类模型
    ├── Comment.java           # 评论模型
    ├── CreateCommentRequest.java # 创建评论请求模型
    ├── CreatePostRequest.java # 创建帖子请求模型
    ├── ErrorResponse.java     # 错误响应模型
    ├── LoginRequest.java      # 登录请求模型
    ├── Pagination.java        # 分页模型
    ├── PostDetail.java        # 帖子详情模型
    ├── PostListItem.java      # 帖子列表项模型
    ├── PostsGet200Response.java # 帖子列表响应模型
    ├── PublicUserProfile.java # 公开用户资料模型
    ├── RegisterRequest.java   # 注册请求模型
    ├── Tag.java               # 标签模型
    ├── UpdatePostRequest.java # 更新帖子请求模型
    ├── UpdateProfileRequest.java # 更新资料请求模型
    └── UserProfile.java       # 用户资料模型
```

## 自动生成的API功能

基于openapi.yaml，已生成以下API功能端点：

1. **用户认证**
   - 注册用户
   - 用户登录
   - 获取当前用户信息
   - 更新用户资料

2. **帖子管理**
   - 获取帖子列表（支持分页、搜索、排序）
   - 获取帖子详情
   - 创建帖子
   - 更新帖子
   - 删除帖子
   - 点赞/取消点赞帖子

3. **评论互动**
   - 获取帖子评论
   - 创建评论
   - 回复评论
   - 点赞评论

4. **版块与标签**
   - 获取所有版块分类
   - 获取所有标签

## 使用方法

### 1. 环境准备

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 2. 数据库配置

在 `src/main/resources/application.properties` 文件中配置数据库连接：

```properties
# 服务器配置
server.port=8080
server.servlet.context-path=/api

# MySQL数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/forum_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect

# OpenAPI文档配置
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# JWT配置
jwt.secret=your_jwt_secret_key
jwt.expiration=3600000
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

### 4. 访问API文档

启动成功后，可以通过以下地址访问Swagger UI：
http://localhost:8080/api/swagger-ui.html

## 开发说明

1. **实现API接口**
   - 创建控制器类实现 `DefaultApi` 接口
   - 实现各个方法的业务逻辑

2. **创建Service层**
   - 实现业务逻辑处理
   - 数据校验和安全控制

3. **创建Repository层**
   - 定义数据访问接口
   - 实现数据库操作

4. **配置安全认证**
   - 实现JWT认证过滤器
   - 配置Spring Security

## 已配置的技术栈

- **框架**：Spring Boot 2.7.15
- **ORM**：Spring Data JPA
- **安全**：Spring Security + JWT
- **数据库**：MySQL
- **API文档**：SpringDoc OpenAPI (Swagger UI)
- **工具**：Lombok

## 注意事项

1. 生成的代码仅包含接口和模型，需要您实现具体的业务逻辑
2. 请确保数据库已创建，且配置正确
3. 生产环境中请修改JWT密钥为强密钥
4. 建议添加日志记录和异常处理机制

## 后续开发建议

1. 实现用户认证与授权
2. 实现帖子、评论的CRUD操作
3. 添加文件上传功能（用户头像、帖子图片）
4. 实现搜索功能
5. 添加缓存机制提高性能
6. 实现限流和防刷机制
