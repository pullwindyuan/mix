package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.module.auth.dto.UserRoleDto;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.futuremap.erp.module.auth.service.impl.ResourceServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 授权用户角色 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/auth/user-role")
@Api(tags = "<角色与权限>：获取用户的角色信息")
public class UserRoleController {
    @Autowired
    private UserRoleServiceImpl userRoleService;

    @PostMapping("/list")
    @ApiOperation("获取用户的角色列表")
    public List<UserRole> findList(@RequestBody @Valid UserRole userRole) {
        return userRoleService.findList(userRole);
    }

    @PostMapping("/list/detail")
    @ApiOperation("获取用户的角色详情信息列表")
    public List<UserRoleDto> findDetailList(@RequestBody @Valid UserRole userRole) {
        return userRoleService.findDetailList(userRole);
    }
}
