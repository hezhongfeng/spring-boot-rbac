package com.example.rbac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static com.example.rbac.security.CustomDsl.customDsl;

@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeRequests(authorizeRequests -> authorizeRequests
        // 开放一些路由
        .antMatchers("/public/**").permitAll()
        // 其他所有的请求都必须经过认证，这里就是登录
        .anyRequest().authenticated());

    http.exceptionHandling()
        // 未登录
        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
        // 权限不足
        .accessDeniedHandler(new MyAccessDeniedHandler());

    // 通过 CustomDsl 来配置自定义的过滤器
    http.apply(customDsl());
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password")
        .roles("USER").build());
    return manager;
  }

}
