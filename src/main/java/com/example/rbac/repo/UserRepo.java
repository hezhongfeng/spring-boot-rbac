package com.example.rbac.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.rbac.entity.User;

/**
 * 
 * JpaRepository 实现了JPA 需要的方法，包括了基本的增删改查和分页等
 * 
 */
public interface UserRepo extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);

  User findByUsername(String username);

  // 使用图查询，不会造成 N+1 问题
  @EntityGraph(value = "user-with-roles")
  @Query(value = "SELECT user FROM User user")
  Page<User> findAllUsersWithRoles(Pageable pageable);

  @EntityGraph(attributePaths = {"roles"})
  @Query(value = "SELECT user FROM User user")
  List<User> findAllUsersWithRoles();

  @EntityGraph(value = "user-with-roles")
  @Query(value = "SELECT user FROM User user WHERE user.id = ?1")
  User findUserWithRoles(Long id);
}
