package com.example.rbac.config;

public class ListRequest {
  private Integer page;
  private Integer pageSize;
  private String sortBy;

  public ListRequest() {}

  public ListRequest(Integer page, Integer pageSize, String sortBy) {
    this.page = page;
    this.pageSize = pageSize;
    this.sortBy = sortBy;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortBy() {
    return this.sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public ListRequest page(Integer page) {
    setPage(page);
    return this;
  }

  public ListRequest pageSize(Integer pageSize) {
    setPageSize(pageSize);
    return this;
  }

  public ListRequest sortBy(String sortBy) {
    setSortBy(sortBy);
    return this;
  }


  @Override
  public String toString() {
    return "{" + " page='" + getPage() + "'" + ", pageSize='" + getPageSize() + "'" + ", sortBy='"
        + getSortBy() + "'" + "}";
  }
}
