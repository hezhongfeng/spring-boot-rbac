package com.example.rbac.controller;

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
import com.example.rbac.payload.CreatePermissionDto;
import com.example.rbac.payload.UpdatePermissionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "权限", description = "权限相关CRUD接口")
@RestController
@RequestMapping("/api/admin/v1/permissions")
public class AdminPermissionController {

  @Operation(summary = "获取权限列表")
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
    // 分页查询

    ListResponse<Permission> listResponse = new ListResponse<Permission>();
    return new RespResult<ListResponse<Permission>>(200, "", listResponse);
  }

  @Operation(summary = "创建权限")
  @PostMapping
  public RespResult<Permission> createPermission(
      @RequestBody @Validated CreatePermissionDto permissionDto) {

    Permission permission = new Permission();
    return new RespResult<Permission>(200, "", permission);
  }


  @Operation(summary = "查看权限")
  @GetMapping("/{id}")
  public RespResult<Object> getPermission(@PathVariable("id") Long id) {
    Permission permission = new Permission();

    return new RespResult<Object>(200, "", permission);
  }

  @Operation(summary = "更新权限")
  @PutMapping("/{id}")
  public RespResult<Object> updatePermission(
      @RequestBody @Validated UpdatePermissionDto permissionDto, @PathVariable("id") Long id) {

    return new RespResult<Object>(200, "", null);
  }

  @Operation(summary = "删除权限")
  @DeleteMapping()
  public RespResult<Object> deleteRoles(
      @RequestBody @Validated DeleteListRequest deleteListRequest) {

    return new RespResult<Object>(200, "", null);
  }

}
