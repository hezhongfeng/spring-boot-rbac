package com.example.rbac.controller;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

@RestController
@RequestMapping("/api/admin/v1/permissions")
public class AdminPermissionController {

  @Autowired
  private PermissionRepo permissionRepo;

  @Autowired
  private PermissionService permissionService;

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


  @GetMapping("/{id}")
  public RespResult<Object> getPermission(@PathVariable("id") Long id) {
    Permission permission = permissionRepo.findById(id).get();

    permission.setRoles(new HashSet<>());

    return new RespResult<Object>(200, "", permission);
  }

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
