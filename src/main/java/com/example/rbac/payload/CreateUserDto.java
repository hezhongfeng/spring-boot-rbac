package com.example.rbac.payload;

import java.util.List;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class CreateUserDto {

  @NotNull(message = "用户名不能为空")
  @NotEmpty(message = "用户名不能为空")
  @Length(min = 4, max = 20, message = "用户名长度必须在4-20之间")
  private String username;

  @NotNull(message = "password 不能为空")
  @NotEmpty(message = "password 不能为空")
  @Length(min = 6, max = 20, message = "密码长度在 6 到 20 个字符")
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*?_]).{6,20}$",
      message = "包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符")
  private String password;

  @Email(message = "邮箱格式不正确")
  private String email;

  private String tel;

  @NotNull(message = "nickname 不能为空")
  private String nickname;

  private String description;

  private List<Long> roleIds;

  private List<Long> clientIds;

  private Long organizationId;

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
    return "{" + " username='" + getUsername() + "'" + ", password='" + getPassword() + "'"
        + ", email='" + getEmail() + "'" + ", tel='" + getTel() + "'" + ", nickname='"
        + getNickname() + "'" + ", description='" + getDescription() + "'" + ", roleIds='"
        + getRoleIds() + "'" + "}";
  }

}

