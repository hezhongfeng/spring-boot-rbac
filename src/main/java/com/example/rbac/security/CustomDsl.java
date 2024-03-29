package com.example.rbac.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 添加我们的 jwt 过滤器，在表单验证之前
    http.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  public static CustomDsl customDsl() {
    return new CustomDsl();
  }
}
