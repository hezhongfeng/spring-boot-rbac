package com.example.rbac;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import com.example.rbac.entity.Permission;
import com.example.rbac.entity.Role;
import com.example.rbac.entity.User;
import com.example.rbac.repo.PermissionRepo;
import com.example.rbac.repo.RoleRepo;
import com.example.rbac.repo.UserRepo;

@SpringBootTest
class RbacApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PermissionRepo permissionRepo;

	@Test
	@Rollback(false)
	void addAdmin() {
		// 新增admin权限
		if (!permissionRepo.existsByKeyName("admin")) {
			Permission permission = new Permission();
			permission.setKeyName("admin");
			permission.setName("管理权限");
			permission.setDescription("管理的权限，属于这个系统的管理员才可以具有的权限");
			permissionRepo.save(permission);
		}

		// 新增管理员角色
		if (!roleRepo.existsByName("管理员角色")) {
			Permission permission = permissionRepo.findByKeyName("admin");
			Set<Permission> permissions = new HashSet<>();
			permissions.add(permission);

			Role role = new Role();
			role.setName("管理员角色");
			role.setDescription("管理员角色，可以管理所有权限");
			role.setPermissions(permissions);
			roleRepo.save(role);
		}

		// 新增管理员用户
		if (!userRepo.existsByUsername("admin")) {
			Role role = roleRepo.findByName("管理员角色");
			Set<Role> roles = new HashSet<>();

			roles.add(role);
			User user = new User();
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("password"));
			user.setRoles(roles);
			user.setNickname("管理员");
			user.setDescription("管理员帐户，可以管理其他用户u、角色和权限");
			userRepo.save(user);
		}

		// 新增普通用户
		if (!userRepo.existsByUsername("normal")) {
			Set<Role> roles = new HashSet<>();
			User user = new User();
			user.setUsername("normal");
			user.setPassword(new BCryptPasswordEncoder().encode("password"));
			user.setRoles(roles);
			user.setNickname("普通用户");
			user.setDescription("我是一个普通用户");
			userRepo.save(user);
		}
	}
}
