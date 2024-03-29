package com.example.rbac.controller;

import java.util.HashSet;
import java.util.List;
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
import com.example.rbac.payload.CreatePermissionDto;
import com.example.rbac.payload.UpdatePermissionDto;
import com.example.rbac.repo.PermissionRepo;
import com.example.rbac.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "权限", description = "权限相关CRUD接口")
@RestController
@RequestMapping("/api/admin/v1/permissions")
@PreAuthorize("@rbacAuthorityService.hasPermissions('admin')") // 必须具有 admin 权限才能访问
public class AdminPermissionController {

  @Autowired
  private PermissionRepo permissionRepo;

  @Autowired
  private PermissionService permissionService;

  @Operation(summary = "查询权限列表")
  @GetMapping
  public RespResult<ListResponse<Permission>> getPermissions(ListRequest listRequest) {

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

    Page<Permission> permissionsPage = permissionService.getAllPermissions(pageable);

    ListResponse<Permission> listResponse = new ListResponse<Permission>();
    listResponse.setCount(permissionsPage.getTotalElements());

    List<Permission> permissionList = permissionsPage.getContent();

    for (Permission permission : permissionList) {
      // 防止引用循环
      permission.setRoles(new HashSet<Role>());
    }

    listResponse.setList(permissionsPage.getContent());
    return new RespResult<ListResponse<Permission>>(200, "", listResponse);
  }

  @Operation(summary = "创建权限")
  @PostMapping
  public RespResult<Permission> createPermission(
      @RequestBody @Validated CreatePermissionDto permissionDto) {

    if (permissionRepo.existsByName(permissionDto.getName())) {
      return new RespResult<Permission>(201, "无法创建，权限名已存在", null);
    }

    if (permissionRepo.existsByKeyName(permissionDto.getKeyName())) {
      return new RespResult<Permission>(201, "无法创建，keyName已存在", null);
    }

    Permission permission = new Permission();
    permission.setName(permissionDto.getName());
    permission.setKeyName(permissionDto.getKeyName());
    permission.setDescription(permissionDto.getDescription());

    permissionRepo.save(permission);

    permission.setRoles(new HashSet<>());
    return new RespResult<Permission>(200, "", permission);
  }

  @Operation(summary = "查看权限")
  @GetMapping("/{id}")
  public RespResult<Object> getPermission(@PathVariable("id") Long id) {
    Permission permission = permissionRepo.findById(id).get();

    permission.setRoles(new HashSet<>());

    return new RespResult<Object>(200, "", permission);
  }

  @Operation(summary = "更新权限")
  @PutMapping("/{id}")
  public RespResult<Object> updatePermission(
      @RequestBody @Validated UpdatePermissionDto permissionDto, @PathVariable("id") Long id) {

    Permission permission = permissionRepo.findByName(permissionDto.getName());

    if (permission != null && !permission.getId().equals(id)) {
      return new RespResult<Object>(201, "无法更新，权限名已存在", null);
    }

    permissionService.updatePermission(permissionDto, id);

    return new RespResult<Object>(200, "", null);
  }

  @Operation(summary = "删除权限")
  @DeleteMapping()
  public RespResult<Object> deleteRoles(
      @RequestBody @Validated DeleteListRequest deleteListRequest) {

    if (!permissionService.canDelete(deleteListRequest.getIds())) {
      return new RespResult<Object>(201, "无法删除，权限已绑定角色", null);
    }

    // 执行删除
    permissionService.deletePermissions(deleteListRequest.getIds());
    return new RespResult<Object>(200, "", null);
  }

}
