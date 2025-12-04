package com.example.forum.api;

import com.example.forum.model.AuthResponse;
import com.example.forum.model.Comment;
import com.example.forum.model.CreateCommentRequest;
import com.example.forum.model.CreatePostRequest;
import com.example.forum.model.Category;
import com.example.forum.model.ErrorResponse;
import com.example.forum.model.LoginRequest;
import com.example.forum.model.Pagination;
import com.example.forum.model.PostDetail;
import com.example.forum.model.PostListItem;
import com.example.forum.model.PostsGet200Response;
import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.RegisterRequest;
import com.example.forum.model.Tag;
import com.example.forum.model.UpdateCommentRequest;
import com.example.forum.model.UpdatePostRequest;
import com.example.forum.model.UpdateProfileRequest;
import com.example.forum.model.UserProfile;
import com.example.forum.service.AuthService;
import com.example.forum.service.UserService;
import com.example.forum.service.PostService;
import com.example.forum.service.CommentService;
import com.example.forum.service.CategoryService;
import com.example.forum.service.TagService;
import com.example.forum.service.PostLikeService;
import com.example.forum.service.CommentLikeService;
import com.example.forum.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DefaultApiImpl implements DefaultApi {

    private final NativeWebRequest request;

    @Autowired
    private JwtTokenService jwtTokenService;

    public DefaultApiImpl(NativeWebRequest request) {
        this.request = request;
    }

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private CommentLikeService commentLikeService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<AuthResponse> authLoginPost(LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<AuthResponse> authRegisterPost(RegisterRequest registerRequest) {
        try {
            // 打印收到的注册请求参数，用于调试
            System.out.println("注册请求参数: username=" + registerRequest.getUsername() + ", email=" + registerRequest.getEmail());
            
            AuthResponse response = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // 打印具体的错误信息
            System.out.println("注册失败: " + e.getMessage());
            // 直接返回错误状态码，不设置body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            // 捕获其他所有异常
            System.out.println("注册时发生未知错误: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> authLogoutPost() {
        HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
        if (httpRequest != null) {
            String token = httpRequest.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                authService.logout(token);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserProfile> usersProfileGet() {
        try {
            Long userId = getCurrentUserId();
            UserProfile profile = userService.getCurrentUserProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            // 当用户不存在时返回NOT_FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // 其他异常视为未授权
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<UserProfile> usersProfilePut(UpdateProfileRequest updateProfileRequest) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            UserProfile profile = userService.updateProfile(userId, updateProfileRequest);
            return ResponseEntity.ok(profile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<PublicUserProfile> usersUserIdGet(Integer userId) {
        try {
            PublicUserProfile profile = userService.getPublicUserProfile(userId.longValue());
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<PostDetail> postsPostIdGet(Integer postId) {
        try {
            PostDetail postDetail = postService.getPostDetail(postId);
            // 增加浏览量
            postService.incrementViewCount(postId.longValue());
            return ResponseEntity.ok(postDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<PostDetail> postsPost(CreatePostRequest createPostRequest) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            PostDetail postDetail = postService.createPost(userId, createPostRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(postDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<PostDetail> postsPostIdPut(Integer postId, UpdatePostRequest updatePostRequest) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            PostDetail postDetail = postService.updatePost(postId, userId, updatePostRequest);
            return ResponseEntity.ok(postDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("没有权限")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @Override
    public ResponseEntity<PostsGet200Response> postsGet(Integer page, Integer limit, String sort, Integer category, String tag) {
        // 设置默认值
        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1 || limit > 100) {
            limit = 10;
        }
        if (sort == null) {
            sort = "time";
        }
        
        try {
            // 调用服务层获取帖子列表
            Page<PostListItem> postsPage = postService.getPosts(page, limit, sort, category, tag);
            
            // 创建分页对象
            Pagination pagination = new Pagination();
            pagination.setPage(page);
            pagination.setLimit(limit);
            pagination.setTotal((int) postsPage.getTotalElements());
            pagination.setTotalPages(postsPage.getTotalPages());
            
            // 创建响应对象
            PostsGet200Response response = new PostsGet200Response();
            response.setPagination(pagination);
            response.setData(postsPage.getContent());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Comment>> postsPostIdCommentsGet(Integer postId, Integer page) {
        // 设置默认值
        if (page == null || page < 1) {
            page = 1;
        }
        
        try {
            // 这里简化实现，实际应该调用服务层
            List<Comment> comments = new ArrayList<>();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Comment> postsPostIdCommentsPost(Integer postId, CreateCommentRequest createCommentRequest) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Comment comment = commentService.createComment(userId, postId.longValue(), createCommentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    // 此方法已删除，因为DefaultApi接口中未定义commentsCommentIdDelete方法
    // @Override
    // public ResponseEntity<Void> commentsCommentIdDelete(Integer commentId) {
    //     Long userId = getCurrentUserId();
    //     if (userId == null) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //     }
    //     try {
    //         commentService.deleteComment(userId, commentId.longValue());
    //         return ResponseEntity.noContent().build();
    //     } catch (IllegalAccessException e) {
    //         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }
    // }

    @Override
    public ResponseEntity<List<Category>> categoriesGet() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Tag>> tagsGet(Integer limit) {
        try {
            List<Tag> tags = tagService.getPopularTags(limit);
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Comment> commentsCommentIdReplyPost(Integer commentId, CreateCommentRequest createCommentRequest) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            // 回复评论逻辑：需要指定父评论ID
            // 1. 获取父评论，从中提取帖子ID
            Comment parentComment = commentService.getCommentById(commentId.longValue());
            // 2. 将请求中的parentId设置为要回复的评论ID
            createCommentRequest.setParentId(commentId);
            // 3. 使用父评论的帖子ID调用createComment方法
            Comment comment = commentService.createComment(Long.valueOf(parentComment.getPostId()), userId, createCommentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 辅助方法：获取当前登录用户ID
    private Long getCurrentUserId() {
        HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
        if (httpRequest != null) {
            Principal principal = httpRequest.getUserPrincipal();
            if (principal != null) {
                String username = principal.getName();
                return userService.getUserIdByUsername(username);
            }
        }
        throw new RuntimeException("用户未登录");
    }

    // 临时测试端点：生成JWT令牌
    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestParam String username) {
        String token = jwtTokenService.generateToken(username);
        return ResponseEntity.ok(token);
    }
}