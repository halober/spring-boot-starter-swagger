package com.test.gitee.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.gitee.model.ResponseEntity;
import com.test.gitee.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "操作用户的api")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@ApiOperation("用户登录")
	@GetMapping("/login")
	public ResponseEntity<User> login(@ApiParam("用户名") @RequestParam String userName,
			@ApiParam("密码") @RequestParam String password) {
		if (!password.equals(DigestUtils.md5DigestAsHex(userName.getBytes())))
			return ResponseEntity.error(800, "用户名或者密码错误，密码必须等于用户名md5结果");
		User user = new User();
		user.setPassword(password);
		user.setUserName(userName);
		return ResponseEntity.success(user);
	}
}
