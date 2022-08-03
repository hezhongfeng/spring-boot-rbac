package com.example.rbac.login.payload;

public class LoginRequest {

  private String username;
  private String password;


  public LoginRequest() {}

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LoginRequest username(String username) {
    setUsername(username);
    return this;
  }

  public LoginRequest password(String password) {
    setPassword(password);
    return this;
  }

  @Override
  public String toString() {
    return "{" + " username='" + getUsername() + "'" + ", password='" + getPassword() + "'" + "}";
  }

}
