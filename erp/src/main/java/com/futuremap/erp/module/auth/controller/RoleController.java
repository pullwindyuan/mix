package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.module.auth.dto.RoleDetailDto;
import com.futuremap.erp.module.auth.dto.RoleDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.service.impl.RoleServiceImpl;
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
 * 授权角色表 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/auth/role")
@Api(tags = "<角色与权限>：获取角色数据")
public class RoleController {

    @Autowired
    RoleServiceImpl roleService;

    @PostMapping("/list")
    @ApiOperation("查询角色列表")
    public List<Role> findList(@RequestBody @Valid Role role) {
        return roleService.findList(role);
    }

    @PostMapping("/list/detail")
    @ApiOperation("查询角色详情信息列表：包括了菜单和数据列权限")
    public List<RoleDetailDto> findDetailList(@RequestBody @Valid Role role) {
        return roleService.findDetailList(role);
    }

    @PostMapping("/list/audit/detail")
    @ApiOperation("查询待审核的角色详情信息列表：包括了菜单和数据列权限")
    public List<RoleDetailDto> findAuditDetailList(@RequestBody @Valid Role role) {
        return roleService.findAuditDetailList(role);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色")
    public boolean add(@RequestBody @Valid List<Role> roles) {
        return roleService.add(roles);
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色")
    public boolean delete(@RequestBody @Valid List<Role> roles) {
        return roleService.delete(roles);
    }

    @PostMapping("/update")
    @ApiOperation("更新角色")
    public boolean update(@RequestBody @Valid List<Role> roles) {
        return roleService.update(roles);
    }
}
