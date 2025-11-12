package com.example.forum.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * CreateCommentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CreateCommentRequest {

  private String content;

  private Integer parentId;

  /**
   * Default constructor
   * @deprecated Use {@link CreateCommentRequest#CreateCommentRequest(String)}
   */
  @Deprecated
  public CreateCommentRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateCommentRequest(String content) {
    this.content = content;
  }

  public CreateCommentRequest content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
  */
  @NotNull @Size(min = 1, max = 1000) 
  @Schema(name = "content", example = "这是一个评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public CreateCommentRequest parentId(Integer parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * 父评论ID，用于回复
   * @return parentId
  */
  
  @Schema(name = "parentId", example = "1", description = "父评论ID，用于回复", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parentId")
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateCommentRequest createCommentRequest = (CreateCommentRequest) o;
    return Objects.equals(this.content, createCommentRequest.content) &&
        Objects.equals(this.parentId, createCommentRequest.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, parentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateCommentRequest {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
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

