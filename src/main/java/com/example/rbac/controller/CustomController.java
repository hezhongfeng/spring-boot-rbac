package com.example.rbac.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.RespResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "自定义接口", description = "配合权限，限制访问")
@RestController
@RequestMapping("/api/v1/custom")
@PreAuthorize("@rbacAuthorityService.hasPermissions('custom')") // 必须具有 custom 权限才能访问
public class CustomController {

  @Operation(summary = "访问资源")
  @GetMapping()
  public RespResult<Object> get() {

    String string = "Custom 接口返回的数据";

    return new RespResult<Object>(200, "", string);
  }
}
