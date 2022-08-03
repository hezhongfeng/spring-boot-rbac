package com.example.rbac.config;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DeleteListRequest {

  @NotNull(message = "ids 不能为空")
  private List<Long> ids;

  public DeleteListRequest() {}

  public DeleteListRequest(List<Long> ids) {
    this.ids = ids;
  }

  public List<Long> getIds() {
    return this.ids;
  }

  public void setIds(List<Long> ids) {
    this.ids = ids;
  }

  @Override
  public String toString() {
    return "{" + " ids='" + getIds() + "'" + "}";
  }

}
