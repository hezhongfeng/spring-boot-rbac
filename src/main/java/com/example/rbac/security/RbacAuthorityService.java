package com.example.rbac.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component("rbacAuthorityService")
public class RbacAuthorityService {

  public boolean hasPermissions(String... permissions) {
    // 拿到上一步设置的所有权限
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 用户所具有的所有权限
    Set<String> set = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    // 接口需要的所有权限，只要有一个满足就可以访问
    for (String permission : permissions) {
      if (set.contains(permission)) {
        return true;
      }
    }

    return false;
  }
}
