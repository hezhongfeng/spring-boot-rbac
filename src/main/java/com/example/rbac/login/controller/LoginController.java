package com.example.rbac.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

  @PostMapping("/logout")
  public RespResult<LoginResult> logout(HttpServletRequest request, HttpServletResponse response) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      new SecurityContextLogoutHandler().logout(request, response, authentication);
    }

    return new RespResult<LoginResult>(200, "登出成功", new LoginResult("", 1L));
  }

  @GetMapping("/current")
  public RespResult<CurrentResult> getCurrentUseer() {
    // 拿到上一步设置的所有权限
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // 获取完整用户信息
    CurrentResult currentUser = userService.getCurrentUser(userDetails.getUsername());

    return new RespResult<CurrentResult>(200, "", currentUser);
  }
}
