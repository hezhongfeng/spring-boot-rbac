package com.example.rbac.login.payload;

public class refreshTokenRequest {

  private String refreshToken;


  public refreshTokenRequest() {}

  public refreshTokenRequest(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getRefreshToken() {
    return this.refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public String toString() {
    return "{" + " refreshToken='" + getRefreshToken() + "'" + "}";
  }

}
