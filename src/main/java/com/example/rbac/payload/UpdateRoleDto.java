package com.example.rbac.payload;

import java.util.List;
import javax.validation.constraints.NotNull;

public class UpdateRoleDto {

  @NotNull(message = "name 不能为空")
  private String name;

  private String description;

  private List<Long> permissionIds;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Long> getPermissionIds() {
    return this.permissionIds;
  }

  public void setPermissionIds(List<Long> permissionIds) {
    this.permissionIds = permissionIds;
  }

}
