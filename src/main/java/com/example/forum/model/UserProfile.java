package com.example.forum.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * UserProfile
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserProfile {

  private Integer id;

  private String username;

  private String email;

  private String avatar;

  private String bio;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime joinDate;

  private Integer postCount;

  private Integer commentCount;

  public UserProfile id(Integer id) {
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

  public UserProfile username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  
  @Schema(name = "username", example = "john_doe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserProfile email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @javax.validation.constraints.Email
  @Schema(name = "email", example = "user@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserProfile avatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  /**
   * 头像URL
   * @return avatar
  */
  
  @Schema(name = "avatar", example = "https://example.com/avatar.jpg", description = "头像URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public UserProfile bio(String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * 个人简介
   * @return bio
  */
  
  @Schema(name = "bio", example = "这是一个示例用户简介", description = "个人简介", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bio")
  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public UserProfile joinDate(OffsetDateTime joinDate) {
    this.joinDate = joinDate;
    return this;
  }

  /**
   * Get joinDate
   * @return joinDate
  */
  @Valid 
  @Schema(name = "joinDate", example = "2023-01-15T10:30Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("joinDate")
  public OffsetDateTime getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(OffsetDateTime joinDate) {
    this.joinDate = joinDate;
  }

  public UserProfile postCount(Integer postCount) {
    this.postCount = postCount;
    return this;
  }

  /**
   * Get postCount
   * @return postCount
  */
  
  @Schema(name = "postCount", example = "42", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("postCount")
  public Integer getPostCount() {
    return postCount;
  }

  public void setPostCount(Integer postCount) {
    this.postCount = postCount;
  }

  public UserProfile commentCount(Integer commentCount) {
    this.commentCount = commentCount;
    return this;
  }

  /**
   * Get commentCount
   * @return commentCount
  */
  
  @Schema(name = "commentCount", example = "156", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commentCount")
  public Integer getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserProfile userProfile = (UserProfile) o;
    return Objects.equals(this.id, userProfile.id) &&
        Objects.equals(this.username, userProfile.username) &&
        Objects.equals(this.email, userProfile.email) &&
        Objects.equals(this.avatar, userProfile.avatar) &&
        Objects.equals(this.bio, userProfile.bio) &&
        Objects.equals(this.joinDate, userProfile.joinDate) &&
        Objects.equals(this.postCount, userProfile.postCount) &&
        Objects.equals(this.commentCount, userProfile.commentCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, avatar, bio, joinDate, postCount, commentCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserProfile {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    avatar: ").append(toIndentedString(avatar)).append("\n");
    sb.append("    bio: ").append(toIndentedString(bio)).append("\n");
    sb.append("    joinDate: ").append(toIndentedString(joinDate)).append("\n");
    sb.append("    postCount: ").append(toIndentedString(postCount)).append("\n");
    sb.append("    commentCount: ").append(toIndentedString(commentCount)).append("\n");
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

