package com.example.rbac.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NamedEntityGraph(name = "role-with-permissions",
    attributeNodes = {@NamedAttributeNode("permissions")})
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 注意，这里设置的unique只针对为创建的数据表有效，如果已经存在的表，需要手动修改下表的属性
  @Column(unique = true)
  private String name;

  @Column(columnDefinition = "text")
  private String description;

  @CreatedDate
  @CreationTimestamp
  private Date createdTime;

  @LastModifiedDate
  @UpdateTimestamp
  private Date updatedTime;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @ManyToMany()
  @JoinTable
  private Set<Permission> permissions;

  public Role() {}

  public Role(String name, String desc) {
    this.name = name;
    this.description = desc;
  }

  public Set<Permission> getPermissions() {
    return this.permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
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

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", description='"
        + getDescription() + "'" + ", createdTime='" + getCreatedTime() + "'" + ", updatedTime='"
        + getUpdatedTime() + "'" + ", users='" + getUsers() + "'" + ", permissions='"
        + getPermissions() + "'" + "}";
  }

}
