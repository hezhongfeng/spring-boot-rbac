package com.example.rbac;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.rbac.service.UserService;

@Component
public class StartInit {

  @Autowired
  private UserService userService;

  // 在启动监听之前就执行
  @PostConstruct
  public void init() {
    System.out.println("执行StartInit时间：" + LocalDateTime.now());

    // 初始化用户
    userService.initAllUsers();
  }
}
