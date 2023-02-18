package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.common.Constants;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import com.futuremap.erp.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 授权用户表 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/auth/user")
@Api(tags = "<角色与权限>：获取用户信息")
@Slf4j
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRoleServiceImpl userRoleService;


    @PostMapping("/list")
    @ApiOperation("查询用户")
    public List<UserDto> findList(@RequestBody @Valid UserDto user) {
        return userService.findList(user);
    }

    @PostMapping("/currentUser")
    @ApiOperation("查询用户")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public void add(@RequestBody @Valid UserDto userdto) {
        try {
            if(userService.selectByPhone(userdto.getPhone()) != null) {
                throw new FuturemapBaseException("该用户已存在，添加失败！");
            }else {
                userService.saveUser(userdto);
            }
        } catch (Exception e) {
            log.error("添加用户失败",e);
            if(e.getMessage().equals("该用户已存在，添加失败！")) {
                throw new FuturemapBaseException("该用户已存在，添加失败！");
            }else {
                throw new FuturemapBaseException("添加用户失败");
            }
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public void delete(@RequestBody @Valid User user) {
        userService.delete(user.getId());
    }


    @PostMapping("/update")
    @ApiOperation("更新用户")
    public void update(@RequestBody  UserDto userdto) {
        //TODO 更新角色
        userService.updateUser(userdto);
    }


    @PostMapping("/reset")
    @ApiOperation("重置密码")
    public void reset(@RequestBody  User user) {
        User dbuser = userService.findOne(user.getId());
        //数据库已加密密码
        String password = dbuser.getPassword();
        //TODO 传过来的秘密如果加密还需解密
        String oldPassword = user.getOldPassword();
        String newPassword = user.getNewPassword();
        //验证旧密码 如果是超级管理员不验证旧密码
        if (!UserUtils.hasRole(Constants.ROLE_SUPER_ADMIN) &&!new BCryptPasswordEncoder().matches(oldPassword,password)){
            throw new FuturemapBaseException("原密码不正确");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userService.updateById(user);
    }



}
