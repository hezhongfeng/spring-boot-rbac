package com.example.rbac.payload;

import java.util.List;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class UpdateUserDto {

  @Email(message = "邮箱格式不正确")
  private String email;

  private String tel;

  @NotNull(message = "nickname 不能为空")
  @Length(min = 2, max = 20, message = "昵称长度为 2-20 个字符")
  private String nickname;

  private String description;

  private List<Long> roleIds;

  private List<Long> clientIds;

  private Long organizationId;

  public UpdateUserDto() {}

  public UpdateUserDto(String email, String tel, String nickname, String description,
      List<Long> roleIds) {
    this.email = email;
    this.tel = tel;
    this.nickname = nickname;
    this.description = description;
    this.roleIds = roleIds;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
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


  public Long getOrganizationId() {
    return this.organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }

  public List<Long> getClientIds() {
    return this.clientIds;
  }

  public void setClientIds(List<Long> clientIds) {
    this.clientIds = clientIds;
  }

  @Override
  public String toString() {
    return "{" + " email='" + getEmail() + "'" + ", tel='" + getTel() + "'" + ", nickname='"
        + getNickname() + "'" + ", description='" + getDescription() + "'" + ", roleIds='"
        + getRoleIds() + "'" + "}";
  }
}

