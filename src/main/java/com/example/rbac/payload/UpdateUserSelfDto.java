package com.example.rbac.payload;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdateUserSelfDto {
  @NotNull(message = "nickname 不能为空")
  @Length(min = 2, max = 20, message = "昵称长度为 2-20 个字符")
  private String nickname;

  private String description;


  public UpdateUserSelfDto() {}

  public UpdateUserSelfDto(String nickname, String description) {
    this.nickname = nickname;
    this.description = description;
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

  @Override
  public String toString() {
    return "{" + " nickname='" + getNickname() + "'" + ", description='" + getDescription() + "'"
        + "}";
  }

}
