package com.example.rbac.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import static com.example.rbac.security.CustomDsl.customDsl;

@EnableWebSecurity
public class SecurityConfig {

  /**
   * 此方法配置的路由不会进入 Spring Security 机制进行验证
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    String[] antMatchersAnonymous = {"/api/v1/login/**", "/public/**"};
    return web -> web.ignoring()
        // 放行所有OPTIONS请求
        .antMatchers(HttpMethod.OPTIONS)
        // 开放一些路由
        .antMatchers(antMatchersAnonymous);
  }

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

    // 错误处理
    http.exceptionHandling()
        // 未登录
        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
        // 权限不足
        .accessDeniedHandler(new MyAccessDeniedHandler());

    // 关闭 CSRF
    http.csrf().disable();

    // 不创建会话
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // 通过 CustomDsl 来配置自定义的过滤器
    http.apply(customDsl());

    return http.build();
  }
}
