package com.example.rbac.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.DeleteListRequest;
import com.example.rbac.config.ListRequest;
import com.example.rbac.config.ListResponse;
import com.example.rbac.config.RespResult;
import com.example.rbac.entity.Permission;
import com.example.rbac.entity.Role;
import com.example.rbac.payload.CreateRoleDto;
import com.example.rbac.payload.UpdateRoleDto;
import com.example.rbac.repo.RoleRepo;
import com.example.rbac.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "角色", description = "角色相关CRUD接口")
@RestController
@RequestMapping("/api/admin/v1/roles")
@PreAuthorize("@rbacAuthorityService.hasPermissions('admin')") // 必须具有 admin 权限才能访问
public class AdminRoleController {

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private RoleService roleService;

  @Operation(summary = "查询角色列表")
  @GetMapping
  public RespResult<ListResponse<Role>> getRoles(ListRequest listRequest) {

    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    int pageIndex = 0;
    int pageSize = 10;
    if (listRequest.getPage() != null) {
      pageIndex = listRequest.getPage() - 1;
    }

    if (listRequest.getPageSize() != null) {
      pageSize = listRequest.getPageSize();
    }
    // 分页
    Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

    Page<Role> rolesPage = roleService.getRolesWithPermissions(pageable);

    ListResponse<Role> listResponse = new ListResponse<Role>();
    listResponse.setCount(rolesPage.getTotalElements());


    List<Role> roles = rolesPage.getContent();

    // 去除关联的users
    for (Role role : roles) {
      // 去除关联的users
      role.setUsers(new HashSet<>());
      // 防止循环引用
      Set<Permission> permissions = role.getPermissions();
      for (Permission permission : permissions) {
        permission.setRoles(new HashSet<Role>());
      }
    }

    listResponse.setList(roles);
    return new RespResult<ListResponse<Role>>(200, "", listResponse);
  }

  @Operation(summary = "创建角色")
  @PostMapping
  public RespResult<Role> createRole(@RequestBody @Validated CreateRoleDto roleDto) {

    if (roleRepo.existsByName(roleDto.getName())) {
      return new RespResult<Role>(201, "无法创建，角色名已存在", null);
    }

    Role role = roleService.addRole(roleDto);
    role.setUsers(new HashSet<>());
    role.setPermissions(new HashSet<>());
    return new RespResult<Role>(200, "", role);
  }

  @Operation(summary = "查看角色")
  @GetMapping("/{id}")
  public RespResult<Object> getRole(@PathVariable("id") Long id) {
    Role role = roleService.getRoleByIdWithPermissions(id);

    role.setUsers(new HashSet<>());
    // 去除关联的users
    role.setUsers(new HashSet<>());
    // 防止循环引用
    Set<Permission> permissions = role.getPermissions();
    for (Permission permission : permissions) {
      permission.setRoles(new HashSet<Role>());
    }

    return new RespResult<Object>(200, "", role);
  }

  @Operation(summary = "更新角色")
  @PutMapping("/{id}")
  public RespResult<Object> updateRole(@RequestBody @Validated UpdateRoleDto roleDto,
      @PathVariable("id") Long id) {

    Role role = roleRepo.findByName(roleDto.getName());

    if (role != null && !role.getId().equals(id)) {
      return new RespResult<Object>(201, "无法更新，角色名已存在", null);
    }

    roleService.updateRole(roleDto, id);

    return new RespResult<Object>(200, "", null);
  }

  @Operation(summary = "删除角色")
  @DeleteMapping()
  public RespResult<Object> deleteRoles(
      @RequestBody @Validated DeleteListRequest deleteListRequest) {

    if (!roleService.canDelete(deleteListRequest.getIds())) {
      return new RespResult<Object>(201, "无法删除，角色已绑定用户", null);
    }

    // 执行删除
    roleService.deleteRoles(deleteListRequest.getIds());
    return new RespResult<Object>(200, "", null);
  }
}
