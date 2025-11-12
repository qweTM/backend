package com.example.forum.model;

import java.net.URI;
import java.util.Objects;
import com.example.forum.model.Category;
import com.example.forum.model.PublicUserProfile;
import com.example.forum.model.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * PostDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PostDetail {

  private Integer id;

  private String title;

  private String excerpt;

  private PublicUserProfile author;

  private Category category;

  @Valid
  private List<@Valid Tag> tags;

  private Integer viewCount;

  private Integer commentCount;

  private Integer likeCount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  private String content;

  public PostDetail id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PostDetail title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  
  @Schema(name = "title", example = "示例帖子标题", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PostDetail excerpt(String excerpt) {
    this.excerpt = excerpt;
    return this;
  }

  /**
   * 内容摘要
   * @return excerpt
  */
  
  @Schema(name = "excerpt", example = "这是内容的摘要...", description = "内容摘要", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("excerpt")
  public String getExcerpt() {
    return excerpt;
  }

  public void setExcerpt(String excerpt) {
    this.excerpt = excerpt;
  }

  public PostDetail author(PublicUserProfile author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public PublicUserProfile getAuthor() {
    return author;
  }

  public void setAuthor(PublicUserProfile author) {
    this.author = author;
  }

  public PostDetail category(Category category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  */
  @Valid 
  @Schema(name = "category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("category")
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public PostDetail tags(List<@Valid Tag> tags) {
    this.tags = tags;
    return this;
  }

  public PostDetail addTagsItem(Tag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
  */
  @Valid 
  @Schema(name = "tags", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<@Valid Tag> getTags() {
    return tags;
  }

  public void setTags(List<@Valid Tag> tags) {
    this.tags = tags;
  }

  public PostDetail viewCount(Integer viewCount) {
    this.viewCount = viewCount;
    return this;
  }

  /**
   * Get viewCount
   * @return viewCount
  */
  
  @Schema(name = "viewCount", example = "150", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("viewCount")
  public Integer getViewCount() {
    return viewCount;
  }

  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }

  public PostDetail commentCount(Integer commentCount) {
    this.commentCount = commentCount;
    return this;
  }

  /**
   * Get commentCount
   * @return commentCount
  */
  
  @Schema(name = "commentCount", example = "12", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commentCount")
  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public PostDetail likeCount(Integer likeCount) {
    this.likeCount = likeCount;
    return this;
  }

  /**
   * Get likeCount
   * @return likeCount
  */
  
  @Schema(name = "likeCount", example = "25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("likeCount")
  public Integer getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(Integer likeCount) {
    this.likeCount = likeCount;
  }

  public PostDetail createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public PostDetail updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
  */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public PostDetail content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
  */
  
  @Schema(name = "content", example = "这是完整的帖子内容...", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostDetail postDetail = (PostDetail) o;
    return Objects.equals(this.id, postDetail.id) &&
        Objects.equals(this.title, postDetail.title) &&
        Objects.equals(this.excerpt, postDetail.excerpt) &&
        Objects.equals(this.author, postDetail.author) &&
        Objects.equals(this.category, postDetail.category) &&
        Objects.equals(this.tags, postDetail.tags) &&
        Objects.equals(this.viewCount, postDetail.viewCount) &&
        Objects.equals(this.commentCount, postDetail.commentCount) &&
        Objects.equals(this.likeCount, postDetail.likeCount) &&
        Objects.equals(this.createdAt, postDetail.createdAt) &&
        Objects.equals(this.updatedAt, postDetail.updatedAt) &&
        Objects.equals(this.content, postDetail.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, excerpt, author, category, tags, viewCount, commentCount, likeCount, createdAt, updatedAt, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostDetail {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    excerpt: ").append(toIndentedString(excerpt)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    viewCount: ").append(toIndentedString(viewCount)).append("\n");
    sb.append("    commentCount: ").append(toIndentedString(commentCount)).append("\n");
    sb.append("    likeCount: ").append(toIndentedString(likeCount)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

