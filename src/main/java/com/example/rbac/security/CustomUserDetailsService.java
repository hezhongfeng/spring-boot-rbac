package com.example.rbac.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.rbac.entity.Role;
import com.example.rbac.entity.User;
import com.example.rbac.repo.PermissionRepo;
import com.example.rbac.repo.RoleRepo;
import com.example.rbac.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Resource
  private UserRepo userRepo;

  @Resource
  private RoleRepo roleRepo;

  @Resource
  private PermissionRepo permissionRepo;

  @Override
  @Transactional // 事务确保了，在查询user的时候是级联查询，会吧user的role和permission也查询出来
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepo.findByUsername(username);

    // 没有这个用户
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

    Set<Role> roles = user.getRoles();

    for (Role role : roles) {
      role.getPermissions().forEach(permission -> {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getKeyName());
        if (!authorities.contains(authority)) {
          authorities.add(authority);
        }
      });
    }

    return org.springframework.security.core.userdetails.User.builder().username(username)
        .password(user.getPassword()).authorities(authorities).build();
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepo.findById(id).orElseThrow(
        () -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

    List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

    Set<Role> roleSet = user.getRoles();

    for (Role role : roleSet) {
      role.getPermissions().forEach(permission -> {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getKeyName());
        if (!authorities.contains(authority)) {
          authorities.add(authority);
        }
      });
    }


    return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
        .password(user.getPassword()).authorities(authorities).build();
  }

}

