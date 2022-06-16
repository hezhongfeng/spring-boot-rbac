package com.example.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.rbac.entity.Role;
import com.example.rbac.entity.User;
import com.example.rbac.payload.CreateUserDto;
import com.example.rbac.payload.CurrentResult;
import com.example.rbac.payload.UpdateUserDto;
import com.example.rbac.repo.RoleRepo;
import com.example.rbac.repo.UserRepo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private UserRepo userRepo;

  @Override
  public Page<User> getAllUsers(Pageable pageable) {
    return userRepo.findAll(pageable);
  }

  @Override
  public Page<User> getAllUsersWithRoles(Pageable pageable) {
    return userRepo.findAllUsersWithRoles(pageable);
  }

  @Override
  public User getUserById(Long id) {
    return userRepo.findById(id).orElse(null);
  }

  @Override
  public User getUserByIdWithRoles(Long id) {
    return userRepo.findUserWithRoles(id);
  }

  @Override
  public User addUser(CreateUserDto userDto) {

    // 处理密码
    String password = new BCryptPasswordEncoder().encode(userDto.getPassword());

    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(password);
    user.setNickname(userDto.getNickname());
    user.setDescription(userDto.getDescription());

    Set<Role> roles = new HashSet<>();

    for (Long roleId : userDto.getRoleIds()) {
      Role role = roleRepo.findById(roleId).get();
      // Set 不需要判断是否包含，因为Set是无序的，所以不会出现重复的元素
      roles.add(role);
    }
    user.setRoles(roles);

    userRepo.save(user);

    return user;
  }

  @Override
  public void updateUser(UpdateUserDto userDto, Long id) {
    User user = userRepo.findById(id).get();
    user.setNickname(userDto.getNickname());
    user.setDescription(userDto.getDescription());

    Set<Role> roles = new HashSet<>();

    for (Long roleId : userDto.getRoleIds()) {
      Role role = roleRepo.findById(roleId).get();
      // Set 不需要判断是否包含，因为Set是无序的，所以不会出现重复的元素
      roles.add(role);
    }
    user.setRoles(roles);

    userRepo.save(user);
  }

  public void updateUserBase(UpdateUserDto userDto, Long id) {
    User user = userRepo.findById(id).get();
    user.setNickname(userDto.getNickname());
    user.setDescription(userDto.getDescription());

    userRepo.save(user);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userRepo.existsByUsername(username);
  }

  @Override
  public void updateUser(User user) {
    User currentUser = userRepo.findById(user.getId()).get();
    currentUser.setNickname(user.getNickname());
    currentUser.setDescription(user.getDescription());
    userRepo.save(currentUser);
  }

  @Override
  public void updateUserPassword(Long id, String password) {
    User currentUser = userRepo.findById(id).get();
    // 处理密码
    String encodePassword = new BCryptPasswordEncoder().encode(password);
    currentUser.setPassword(encodePassword);
    userRepo.save(currentUser);
  }

  @Override
  @Transactional
  public CurrentResult getCurrentUser(String username) {
    User user = userRepo.findByUsername(username);

    CurrentResult currentResult = new CurrentResult();

    currentResult.setUserId(user.getId());
    currentResult.setUsername(user.getUsername());
    currentResult.setNickname(user.getNickname());
    currentResult.setDescription(user.getDescription());

    Set<Role> roles = user.getRoles();

    List<String> permissions = new ArrayList<String>();;

    for (Role role : roles) {
      role.getPermissions().forEach(permission -> {
        String permissionKeyName = permission.getKeyName();
        if (!permissions.contains(permissionKeyName)) {
          permissions.add(permissionKeyName);
        }
      });
    }
    currentResult.setPermissions(permissions);

    return currentResult;
  }

  public void deleteUsers(List<Long> ids) {

    for (Long id : ids) {
      // 注意，此时会将所有的用户角色关联关系也一并删除
      userRepo.deleteById(id);
    }
  }
}
