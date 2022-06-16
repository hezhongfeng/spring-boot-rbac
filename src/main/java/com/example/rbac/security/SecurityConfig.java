package com.example.rbac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeRequests(authorizeRequests -> authorizeRequests
        // 对根路由放行
        .antMatchers("/").permitAll()
        // 其他所有的请求都必须经过认证，这里就是登录
        .anyRequest().authenticated());

    // 开启默认的表单登录
    http.formLogin();

    return http.build();
  }

  @Bean
  UserDetailsService users() {
    // 开发环境使用的内存用户
    UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password")
        .roles("USER").build();
    return new InMemoryUserDetailsManager(user);
  }
}
