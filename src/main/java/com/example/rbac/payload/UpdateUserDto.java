package com.example.rbac.payload;

import java.util.List;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class UpdateUserDto {

  @NotNull(message = "nickname 不能为空")
  @Length(min = 2, max = 20, message = "昵称长度为 2-20 个字符")
  private String nickname;

  private String description;

  private List<Long> roleIds;

  public UpdateUserDto() {}

  public UpdateUserDto(String nickname, String description, List<Long> roleIds) {
    this.nickname = nickname;
    this.description = description;
    this.roleIds = roleIds;
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

  public List<Long> getRoleIds() {
    return this.roleIds;
  }

  public void setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds;
  }

  @Override
  public String toString() {
    return "{" +
      " nickname='" + getNickname() + "'" +
      ", description='" + getDescription() + "'" +
      ", roleIds='" + getRoleIds() + "'" +
      "}";
  }

}

