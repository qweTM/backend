package com.example.forum.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * CreatePostRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CreatePostRequest {

  private String title;

  private String content;

  private Integer categoryId;

  @Valid
  private List<String> tags;

  /**
   * Default constructor
   * @deprecated Use {@link CreatePostRequest#CreatePostRequest(String, String, Integer)}
   */
  @Deprecated
  public CreatePostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreatePostRequest(String title, String content, Integer categoryId) {
    this.title = title;
    this.content = content;
    this.categoryId = categoryId;
  }

  public CreatePostRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @NotNull @Size(min = 5, max = 200) 
  @Schema(name = "title", example = "这是一个示例帖子标题", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public CreatePostRequest content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
  */
  @NotNull @Size(min = 10) 
  @Schema(name = "content", example = "这是帖子的详细内容...", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CreatePostRequest categoryId(Integer categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * Get categoryId
   * @return categoryId
  */
  @NotNull 
  @Schema(name = "categoryId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("categoryId")
  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public CreatePostRequest tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public CreatePostRequest addTagsItem(String tagsItem) {
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
  
  @Schema(name = "tags", example = "[\"技术\",\"编程\",\"Python\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreatePostRequest createPostRequest = (CreatePostRequest) o;
    return Objects.equals(this.title, createPostRequest.title) &&
        Objects.equals(this.content, createPostRequest.content) &&
        Objects.equals(this.categoryId, createPostRequest.categoryId) &&
        Objects.equals(this.tags, createPostRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, categoryId, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreatePostRequest {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

