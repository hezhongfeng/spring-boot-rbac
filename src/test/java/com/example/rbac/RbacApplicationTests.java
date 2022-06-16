package com.example.rbac;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.example.rbac.payload.CreateUserDto;
import com.example.rbac.service.UserService;

@SpringBootTest
class RbacApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	@Rollback(false)
	void contextLoads() {
		CreateUserDto userDto = new CreateUserDto();
		userDto.setUsername("admin1");
		userDto.setPassword("password");
		userDto.setRoleIds(new ArrayList<Long>());
		this.userService.addUser(userDto);
	}

}
