package com.example.rbac.payload;

import java.util.List;

public class CurrentResult {

  private Long userId;

  private String username;

  private String nickname;

  private String description;

  private List<String> permissions;

  public CurrentResult() {}

  public CurrentResult(Long userId, String username, String nickname, String description,
      List<String> permissions) {
    this.userId = userId;
    this.username = username;
    this.nickname = nickname;
    this.description = description;
    this.permissions = permissions;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getPermissions() {
    return this.permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  @Override
  public String toString() {
    return "{" + " userId='" + getUserId() + "'" + ", username='" + getUsername() + "'"
        + ", nickname='" + getNickname() + "'" + ", description='" + getDescription() + "'"
        + ", permissions='" + getPermissions() + "'" + "}";
  }

}


