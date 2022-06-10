package com.example.rbac.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 注意，这里设置的unique只针对为创建的数据表有效，如果已经存在的表，需要手动修改下表的属性
  @Column(unique = true)
  private String name;

  // 注意，这里设置的unique只针对为创建的数据表有效，如果已经存在的表，需要手动修改下表的属性
  @Column(unique = true)
  private String keyName;

  @Column(columnDefinition = "text")
  private String description;

  @CreatedDate
  @CreationTimestamp
  private Date createdTime;

  @LastModifiedDate
  @UpdateTimestamp
  private Date updatedTime;

  /**
   * mappedBy 表明多对多的关系是通过 role 来维护的，而不是通过 permission 来维护的
   */
  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles;

  public Permission() {}

  public Permission(String name, String keyName, String description) {
    this.name = name;
    this.keyName = keyName;
    this.description = description;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Date getCreatedTime() {
    return this.createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public Date getUpdatedTime() {
    return this.updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", keyName='"
        + getKeyName() + "'" + ", description='" + getDescription() + "'" + ", createdTime='"
        + getCreatedTime() + "'" + ", updatedTime='" + getUpdatedTime() + "'" + ", roles='"
        + getRoles() + "'" + "}";
  }

}
