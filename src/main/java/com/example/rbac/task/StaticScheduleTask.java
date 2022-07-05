package com.example.rbac.task;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.example.rbac.service.UserService;

@Configuration
@EnableScheduling
public class StaticScheduleTask {

  @Autowired
  private UserService userService;

  // 每天执行，0时0分0秒执行
  @Scheduled(cron = "0 0 0 * * *")
  private void configureTask() {
    System.out.println("执行静态定时任务时间：" + LocalDateTime.now());

    // 初始化用户
    userService.clearAllUsers();

    userService.initAllUsers();
  }
}
