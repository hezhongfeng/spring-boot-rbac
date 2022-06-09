package com.example.rbac.config;

import java.util.List;

public class ListResponse<T> {
  private long count;
  private List<T> list;

  public ListResponse() {}

  public ListResponse(long count, List<T> list) {
    this.count = count;
    this.list = list;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public List<T> getList() {
    return this.list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "{" + " count='" + getCount() + "'" + ", list='" + getList() + "'" + "}";
  }
}
