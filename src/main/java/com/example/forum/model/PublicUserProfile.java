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
 * PublicUserProfile
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PublicUserProfile {

  private Integer id;

  private String username;

  private String avatar;

  private String bio;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime joinDate;

  private Integer postCount;

  private Integer commentCount;

  public PublicUserProfile id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PublicUserProfile username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  
  @Schema(name = "username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public PublicUserProfile avatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  /**
   * Get avatar
   * @return avatar
  */
  
  @Schema(name = "avatar", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("avatar")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public PublicUserProfile bio(String bio) {
    this.bio = bio;
    return this;
  }

  /**
   * Get bio
   * @return bio
  */
  
  @Schema(name = "bio", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bio")
  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public PublicUserProfile joinDate(OffsetDateTime joinDate) {
    this.joinDate = joinDate;
    return this;
  }

  /**
   * Get joinDate
   * @return joinDate
  */
  @Valid 
  @Schema(name = "joinDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("joinDate")
  public OffsetDateTime getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(OffsetDateTime joinDate) {
    this.joinDate = joinDate;
  }

  public PublicUserProfile postCount(Integer postCount) {
    this.postCount = postCount;
    return this;
  }

  /**
   * Get postCount
   * @return postCount
  */
  
  @Schema(name = "postCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("postCount")
  public Integer getPostCount() {
    return postCount;
  }

  public void setPostCount(Integer postCount) {
    this.postCount = postCount;
  }

  public PublicUserProfile commentCount(Integer commentCount) {
    this.commentCount = commentCount;
    return this;
  }

  /**
   * Get commentCount
   * @return commentCount
  */
  
  @Schema(name = "commentCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    PublicUserProfile publicUserProfile = (PublicUserProfile) o;
    return Objects.equals(this.id, publicUserProfile.id) &&
        Objects.equals(this.username, publicUserProfile.username) &&
        Objects.equals(this.avatar, publicUserProfile.avatar) &&
        Objects.equals(this.bio, publicUserProfile.bio) &&
        Objects.equals(this.joinDate, publicUserProfile.joinDate) &&
        Objects.equals(this.postCount, publicUserProfile.postCount) &&
        Objects.equals(this.commentCount, publicUserProfile.commentCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, avatar, bio, joinDate, postCount, commentCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PublicUserProfile {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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

