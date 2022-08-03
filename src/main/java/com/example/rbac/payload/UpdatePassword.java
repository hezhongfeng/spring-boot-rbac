package com.example.rbac.payload;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class UpdatePassword {
  private String password;

  @NotNull(message = "newPassword 不能为空")
  @NotEmpty(message = "newPassword 不能为空")
  @Length(min = 6, max = 20, message = "密码长度在 6 到 20 个字符")
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*?_]).{6,20}$",
      message = "包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符")
  private String newPassword;

  public UpdatePassword() {}

  public UpdatePassword(String password, String newPassword) {
    this.password = password;
    this.newPassword = newPassword;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public UpdatePassword password(String password) {
    setPassword(password);
    return this;
  }

  public UpdatePassword newPassword(String newPassword) {
    setNewPassword(newPassword);
    return this;
  }

  @Override
  public String toString() {
    return "{" + " password='" + getPassword() + "'" + ", newPassword='" + getNewPassword() + "'"
        + "}";
  }

}
