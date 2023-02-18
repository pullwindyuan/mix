package com.futuremap.custom.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.UserDetail;
import com.futuremap.custom.dto.user.UserCreate;
import com.futuremap.custom.dto.user.UserUpdate;
import com.futuremap.custom.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "用户接口", description = "用户接口等")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;

	@PostMapping("/users")
    @ApiOperation("添加用户")
	@CustomResponse
    public void createUser(@RequestBody UserCreate userCreate) {
        userService.createUser(userCreate);
    }
	
	
	@GetMapping("/users")
    @ApiOperation("查询用户列表")
	@CustomResponse
    public List<UserDetail> queryUser() {
        return userService.queryUser();
    }
	
	@GetMapping("/users/me")
    @ApiOperation("查看当前用户详情")
	@CustomResponse
    public UserDetail me() {
        return userService.getUser();
    }
	
	@PutMapping("/users/me")
    @ApiOperation("修改本身用户")
	@CustomResponse
    public void udpateMyself(@RequestBody UserUpdate userUpdate) {
		//只能修改自己信息
		userUpdate.setId((String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0));
        userService.updateUser(userUpdate);
    }
	
	@PutMapping("/users")
    @ApiOperation("修改用户信息")
	@CustomResponse
    public void udpateUser(@Valid @RequestBody UserUpdate userUpdate) {
        userService.updateUser(userUpdate);
    }
	
	

	
}
