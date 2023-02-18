package com.futuremap.erp.module.auth.controller;


import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
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
@RequestMapping("/auth/role-column")
@Api(tags = "<角色与权限>：获取角色对应的列操作权限信息")
public class RoleColumnController {
    @Autowired
    private RoleColumnServiceImpl roleColumnService;

    @PostMapping("/list")
    @ApiOperation("获取角色对应的列操作权限信息列表")
    public List<RoleColumnDto> findList(@RequestBody @Valid RoleColumn roleColumn) {
        return roleColumnService.findList(roleColumn);
    }

    @PostMapping("/add")
    @ApiOperation("添加角色列操作权限")
    public boolean add(@RequestBody @Valid List<RoleColumn> roleColumns) {
        return roleColumnService.addPermission(roleColumns);
    }

    @PostMapping("/delete")
    @ApiOperation("删除角色列操作权限")
    public boolean delete(@RequestBody @Valid  List<RoleColumn> roleColumns) {
        return roleColumnService.deletePermission(roleColumns);
    }

    @PostMapping("/update")
    @ApiOperation("更新角色列操作权限")
    public boolean update(@RequestBody @Valid  List<RoleColumn> roleColumns) {
        return roleColumnService.updatePermission(roleColumns);
    }
}
