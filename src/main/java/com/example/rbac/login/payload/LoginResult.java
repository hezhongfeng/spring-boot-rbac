package com.example.rbac.login.payload;

public class LoginResult {

  private String token;

  private Long userId;

  public LoginResult() {}

  public LoginResult(String token, Long userId) {
    this.token = token;
    this.userId = userId;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "{" +
      " token='" + getToken() + "'" +
      ", userId='" + getUserId() + "'" +
      "}";
  }
}


