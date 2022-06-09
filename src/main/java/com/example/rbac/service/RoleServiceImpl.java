package com.example.rbac.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.rbac.entity.Permission;
import com.example.rbac.entity.Role;
import com.example.rbac.payload.CreateRoleDto;
import com.example.rbac.payload.UpdateRoleDto;
import com.example.rbac.repo.PermissionRepo;
import com.example.rbac.repo.RoleRepo;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private PermissionRepo permissionRepo;

  public Page<Role> getAllRoles(Pageable pageable) {
    return roleRepo.findAll(pageable);
  }

  public Page<Role> getRolesWithPermissions(Pageable pageable) {
    return roleRepo.findRolesWithPermissions(pageable);
  }

  public Role addRole(CreateRoleDto roleDto) {
    Role role = new Role();
    role.setName(roleDto.getName());
    role.setDescription(roleDto.getDescription());

    Set<Permission> permissions = new HashSet<>();

    for (Long permissionId : roleDto.getPermissionIds()) {
      Permission permission = permissionRepo.findById(permissionId).get();
      // Set 不需要判断是否包含，因为Set是无序的，所以不会出现重复的元素
      permissions.add(permission);
    }
    role.setPermissions(permissions);
    role.setUsers(new HashSet<>());

    roleRepo.save(role);

    return role;
  }

  @Override
  public Role getRoleByIdWithPermissions(Long id) {
    return roleRepo.findWithRelations(id);
  }


  @Override
  public Role updateRole(UpdateRoleDto roleDto, Long id) {
    Role role = roleRepo.findById(id).get();
    role.setName(roleDto.getName());
    role.setDescription(roleDto.getDescription());

    Set<Permission> permissions = new HashSet<>();

    for (Long permissionId : roleDto.getPermissionIds()) {
      Permission permission = permissionRepo.findById(permissionId).get();
      // Set 不需要判断是否包含，因为Set是无序的，所以不会出现重复的元素
      permissions.add(permission);
    }
    role.setPermissions(permissions);
    roleRepo.save(role);

    return role;
  }

  @Override
  public Boolean canDelete(List<Long> ids) {

    // 只要有一个在使用就不允许删除
    for (Long id : ids) {
      Role role = roleRepo.findWithUsers(id);
      if (role.getUsers().size() > 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void deleteRoles(List<Long> ids) {
    for (Long id : ids) {
      roleRepo.deleteById(id);
    }
  }

}
