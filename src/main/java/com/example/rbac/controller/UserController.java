package com.example.rbac.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.RespResult;
import com.example.rbac.entity.User;
import com.example.rbac.payload.UpdatePassword;
import com.example.rbac.payload.UpdateUserSelfDto;
import com.example.rbac.repo.UserRepo;
import com.example.rbac.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
class UserController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserService userService;

  @PutMapping("/{id}")
  public RespResult<Object> UpdateUser(@RequestBody @Validated UpdateUserSelfDto updateUser,
      @PathVariable("id") Long id) {

    // 首先检查是否和当前用户匹配
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = Long.parseLong(authentication.getPrincipal().toString());
    User contentUser = userRepo.findById(userId).get();

    if (contentUser.getId() != id) {
      return new RespResult<Object>(201, "权限错误", null);
    }

    userService.updateUserBase(updateUser, id);

    return new RespResult<Object>(200, "", null);
  }

  @PutMapping("/{id}/password")
  public RespResult<String> UpdateUserPassword(
      @RequestBody @Validated UpdatePassword updatePassword, @PathVariable("id") Long id) {
    // 首先检查是否和当前用户匹配
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Long userId = Long.parseLong(authentication.getPrincipal().toString());
    User contentUser = userRepo.findById(userId).get();

    if (contentUser.getId() != id) {
      return new RespResult<String>(201, "权限错误", null);
    }

    User user = userRepo.findById(id).get();

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (!bCryptPasswordEncoder.matches(updatePassword.getPassword(), user.getPassword())) {
      return new RespResult<String>(201, "原密码错误", null);
    }

    userService.updateUserPassword(id, updatePassword.getNewPassword());

    return new RespResult<String>(200, "", null);
  }

}
