package com.example.rbac.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
  @Override
  public void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

    // 添加我们的 jwt 过滤器
    http.addFilter(new JWTFilter(authenticationManager));
  }

  public static CustomDsl customDsl() {
    return new CustomDsl();
  }
}
