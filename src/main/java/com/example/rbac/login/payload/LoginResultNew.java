package com.example.rbac.login.payload;

public class LoginResultNew {

  private String token;

  private String refreshToken;

  private Long userId;

  public LoginResultNew() {}

  public LoginResultNew(String token, String refreshToken, Long userId) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.userId = userId;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return this.refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "{" + " token='" + getToken() + "'" + ", refreshToken='" + getRefreshToken() + "'"
        + ", userId='" + getUserId() + "'" + "}";
  }
}


