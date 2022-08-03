package com.example.rbac.security;

import com.example.rbac.config.RespResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    response.setContentType("application/json;charset=utf-8");
    RespResult<String> resp = new RespResult<String>(201, "未登录，请先登录", null);
    objectMapper.writeValue(response.getWriter(), resp);
  }
}
