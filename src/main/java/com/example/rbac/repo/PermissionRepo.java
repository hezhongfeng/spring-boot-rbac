package com.example.rbac.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.rbac.entity.Permission;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {

  Permission findByName(String name);

  Permission findByKeyName(String name);

  Boolean existsByName(String name);

  Boolean existsByKeyName(String keyName);

  @EntityGraph(value = "permission-with-roles")
  @Query(value = "SELECT permission FROM Permission permission WHERE permission.id = ?1")
  Permission findWithRoles(Number id);

}
