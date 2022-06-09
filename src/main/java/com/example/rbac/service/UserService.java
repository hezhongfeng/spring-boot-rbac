package com.example.rbac.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.rbac.entity.User;
import com.example.rbac.payload.CreateUserDto;
import com.example.rbac.payload.CurrentResult;
import com.example.rbac.payload.UpdateUserDto;

public interface UserService {
  public Page<User> getAllUsers(Pageable pageable);

  public Page<User> getAllUsersWithRoles(Pageable pageable);

  public User getUserById(Long id);

  public User getUserByIdWithRoles(Long id);

  public User addUser(CreateUserDto userDto);

  public void updateUser(UpdateUserDto userDto, Long id);

  public void updateUserBase(UpdateUserDto userDto, Long id);

  public Boolean existsByUsername(String username);

  public void updateUser(User user);

  public void updateUserPassword(Long id, String password);

  public CurrentResult getCurrentUser(String username);

  public void deleteUsers(List<Long> ids);

}


