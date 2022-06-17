package com.example.rbac.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.RespResult;
import com.example.rbac.entity.User;
import com.example.rbac.login.payload.LoginRequest;
import com.example.rbac.login.payload.LoginResult;
import com.example.rbac.payload.CurrentResult;
import com.example.rbac.repo.UserRepo;
import com.example.rbac.security.JWTProvider;
import com.example.rbac.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private UserService userService;

  /**
   * 登录
   */
  @PostMapping("/login")
  public RespResult<LoginResult> login(@RequestBody LoginRequest login) {

    String username = login.getUsername();
    String password = login.getPassword();

    User user = userRepo.findByUsername(username);

    if (user == null || !(new BCryptPasswordEncoder().matches(password, user.getPassword()))) {
      // 认证失败，返回错误信息
      return new RespResult<LoginResult>(201, "帐户或密码错误", null);
    }

    // 获取完整用户信息
    CurrentResult currentUser = userService.getCurrentUser(login.getUsername());

    // 使用用户 Id 做 subject和使用权限生成token
    String token = JWTProvider.generateToken(Long.toString(currentUser.getUserId()),
        currentUser.getPermissions());

    return new RespResult<LoginResult>(200, "登录成功",
        new LoginResult(token, currentUser.getUserId()));
  }

  @GetMapping("/current")
  public RespResult<CurrentResult> getCurrentUseer() {
    // 拿到上一步设置的所有权限
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Long userId = Long.parseLong(authentication.getPrincipal().toString());

    User user = userRepo.findById(userId).get();

    // 获取完整用户信息
    CurrentResult currentUser = userService.getCurrentUser(user.getUsername());

    return new RespResult<CurrentResult>(200, "", currentUser);
  }
}
