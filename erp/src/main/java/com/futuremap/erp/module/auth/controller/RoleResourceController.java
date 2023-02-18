package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.futuremap.erp.module.auth.service.impl.RoleResourceServiceImpl;
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
 * 授权角色功能表 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/auth/role-resource")
@Api(tags = "<角色与权限>：获取角色对应的菜单权限信息")
public class RoleResourceController {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;

    @PostMapping("/list")
    @ApiOperation("获取全新角色以及空白菜单及列数据权限详情列表，一般用于创建完成一个新角色后获取初始化数据")
    public List<RoleResourceDto> findList(@RequestBody @Valid RoleResource RoleResource) {
        return roleResourceService.findList(RoleResource);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色菜单权限")
    public boolean add(@RequestBody @Valid List<RoleResource> roleResources) {
        return roleResourceService.addPermission(roleResources);
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色菜单权限")
    public boolean delete(@RequestBody @Valid List<RoleResource> roleResources) {
        return roleResourceService.deletePermission(roleResources);
    }

    @PostMapping("/update")
    @ApiOperation("更新角色菜单权限")
    public boolean update(@RequestBody @Valid List<RoleResource> roleResources) {
        return roleResourceService.saveOrUpdateBatch(roleResources);
    }
}
