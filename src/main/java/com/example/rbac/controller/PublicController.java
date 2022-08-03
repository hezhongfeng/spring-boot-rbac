package com.example.rbac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.RespResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "公共接口", description = "没有任何限制就可以访问")
@RestController
@RequestMapping("/api/v1/public")
class PublicController {

  @Operation(summary = "查询用户")
  @GetMapping()
  public RespResult<Object> get() {

    String string = "公共接口";

    return new RespResult<Object>(200, "", string);
  }

}
