package com.example.rbac.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@NamedEntityGraph(name = "user-with-roles",
    attributeNodes = {@NamedAttributeNode("roles")})
public class User {
    @Id // 这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
    private Long id;

    // 注意，这里设置的unique只针对为创建的数据表有效，如果已经存在的表，需要手动修改下表的属性
    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String nickname;

    // 注意 ，不能用desc，因为desc是关键字
    @Column(columnDefinition = "text")
    private String description;

    // 默认加载是 LAZY,FetchType.EAGER 表示立即加载；当使用CascadeType.MERGE时，代表当父对象更新里的子对象更新时，更新操作会传递到子对象
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable
    private Set<Role> roles;

    @CreatedDate
    @CreationTimestamp
    private Date createdTime;

    @LastModifiedDate
    @UpdateTimestamp
    private Date updatedTime;

    public User() {}

    public User(String username, String password, String email, String tel, String nickname,
            String description) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "{" + " id='" + getId() + "'" + ", username='" + getUsername() + "'" + ", password='"
                + getPassword() + "'" + ", nickname='" + getNickname() + "'" + ", description='"
                + getDescription() + "'" + ", roles='" + getRoles() + "'" + ", createdTime='"
                + getCreatedTime() + "'" + ", updatedTime='" + getUpdatedTime() + "'" + "}";
    }

}


