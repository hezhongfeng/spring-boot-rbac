package com.example.rbac.payload;

import javax.validation.constraints.NotNull;

public class CreatePermissionDto {

  @NotNull(message = "name 不能为空")
  private String name;

  @NotNull(message = "keyName 不能为空")
  private String keyName;

  private String description;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKeyName() {
    return this.keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
