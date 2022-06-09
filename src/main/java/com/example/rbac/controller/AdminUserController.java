package com.example.rbac.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rbac.config.DeleteListRequest;
import com.example.rbac.config.ListRequest;
import com.example.rbac.config.ListResponse;
import com.example.rbac.config.RespResult;
import com.example.rbac.entity.Role;
import com.example.rbac.entity.User;
import com.example.rbac.payload.CreateUserDto;
import com.example.rbac.payload.SetPassword;
import com.example.rbac.payload.UpdateUserDto;
import com.example.rbac.service.UserService;


@RestController
@RequestMapping("/api/admin/v1/users")
class AdminUserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public RespResult<ListResponse<User>> getUsers(ListRequest listRequest) {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    int pageIndex = 0;
    int pageSize = 10;
    if (listRequest.getPage() != null) {
      pageIndex = listRequest.getPage() - 1;
    }

    if (listRequest.getPageSize() != null) {
      pageSize = listRequest.getPageSize();
    }
    // 分页
    Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

    Page<User> usersPage = userService.getAllUsersWithRoles(pageable);

    ListResponse<User> listResponse = new ListResponse<User>();
    listResponse.setCount(usersPage.getTotalElements());
    listResponse.setList(usersPage.getContent());
    List<User> userList = usersPage.getContent();

    // 去除密码
    for (User user : userList) {
      user.setPassword("");
      // 防止引用循环
      Set<Role> roles = user.getRoles();
      for (Role role : roles) {
        role.setUsers(new HashSet<>());
        role.setPermissions(new HashSet<>());
      }

    }

    return new RespResult<ListResponse<User>>(200, "", listResponse);
  }

  @PostMapping
  public RespResult<Object> createUser(@RequestBody @Validated CreateUserDto userDto) {
    if (userService.existsByUsername(userDto.getUsername())) {
      return new RespResult<Object>(201, "用户名重复", null);
    }
    userService.addUser(userDto);
    return new RespResult<Object>(200, "", null);
  }

  @GetMapping("/{id}")
  public RespResult<Object> getUser(@PathVariable("id") Long id, HttpServletResponse response) {
    User user = userService.getUserByIdWithRoles(id);

    // 去除密码信息
    user.setPassword("");

    // 防止引用循环
    Set<Role> roles = user.getRoles();
    for (Role role : roles) {
      role.setUsers(new HashSet<>());
      role.setPermissions(new HashSet<>());
    }

    return new RespResult<Object>(200, "", user);
  }

  @PutMapping("/{id}")
  public RespResult<Object> UpdateUser(@RequestBody @Validated UpdateUserDto updateUser,
      @PathVariable("id") Long id, HttpServletResponse response) {

    userService.updateUser(updateUser, id);

    return new RespResult<Object>(200, "", null);
  }

  @PutMapping("/{id}/password")
  public RespResult<String> UpdateUserPassword(@RequestBody SetPassword setPassword,
      @PathVariable("id") Long id) {

    userService.updateUserPassword(id, setPassword.getNewPassword());

    return new RespResult<String>(200, "", null);
  }

  @DeleteMapping()
  public RespResult<Object> deleteUser(
      @RequestBody @Validated DeleteListRequest deleteListRequest) {

    userService.deleteUsers(deleteListRequest.getIds());
    return new RespResult<Object>(200, "", null);
  }

}
