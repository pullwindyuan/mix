package com.futuremap.erp.module.auth.controller;

import com.futuremap.erp.module.auth.dto.ResourceDto;
import com.futuremap.erp.module.auth.dto.RoleDetailDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.RolePermission;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleResourceServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleServiceImpl;
import com.futuremap.erp.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 授权角色功能表 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/auth/permition")
@Api(tags = "<角色与权限>：获取角色对应的权限信息:包括菜单和列信息")
public class RolePermissionController {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;
    @Autowired
    private RoleColumnServiceImpl roleColumnService;
    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("/update")
    @ApiOperation("更新角色权限")
    public boolean update(@RequestBody @Valid RoleDetailDto rolePermission) {
        List<RoleResource> roleResourceList = new ArrayList<>();
        List<RoleColumn> roleColumnList = new ArrayList<>();
        List<RoleResource> roleResourceDeleteList = new ArrayList<>();

        List<RoleResource> allRoleResourceList = new ArrayList<>();
        List<RoleColumn> allRoleColumnList = new ArrayList<>();

        RoleResourceDto roleResourceDto = rolePermission.getRoleResource();
        List<ResourceDto> resourceDtoList = roleResourceDto.getResourceDtoList();
        Map<String, RoleColumn> roleColumnMap = new HashMap<>();
        //List<ResourceDto> resourceDtoList = roleResourceDtoList.
            resourceDtoList.forEach(rd->{
                //提取一个RoleResource
                RoleResource roleResource = new RoleResource(roleResourceDto.getRoleId(), rd.getId());
                allRoleResourceList.add(roleResource);
                if(rd.getStatus() != null && rd.getStatus() == 1) {
                    roleResourceList.add(roleResource);
                    rd.getColumnDtoList().forEach(cd->{
                        cd.getColumns().forEach(c->{
                            //提取一个RoleColumn
                            String key = roleResourceDto.getRoleId() + "-" + c.getTableName() + "-"  + c.getId();
                            if(roleColumnMap.get(key) != null) {
                                return;
                            }
                            RoleColumn roleColumn = new RoleColumn(roleResourceDto.getRoleId(), c.getTableName(), c.getId(), c.getColumnFiled(), c.getColumnName());
                            roleColumn.setRoleName(rolePermission.getRoleName());

                            if(c.getStatus() != null && c.getStatus() == 1) {
                                 allRoleColumnList.add(roleColumn);
                                roleColumnList.add(roleColumn);
                            }
//
//                            roleColumnDeleteList.add(roleColumn);
                            roleColumnMap.put(key, roleColumn);
                        });
                    });
                }
                roleResourceDeleteList.add(roleResource);
            });

        roleService.updatePermission(roleResourceDto.getRoleId(), roleColumnList, roleResourceList);
        return true;
    }

    @PostMapping("/Audit")
    @ApiOperation("更新审核通过的角色权限")
    public boolean Audit(@RequestBody @Valid RoleDetailDto rolePermission) {
        List<RoleResource> roleResourceList = new ArrayList<>();
        List<RoleColumn> roleColumnList = new ArrayList<>();
        List<RoleResource> roleResourceDeleteList = new ArrayList<>();

        List<RoleResource> allRoleResourceList = new ArrayList<>();
        List<RoleColumn> allRoleColumnList = new ArrayList<>();

        RoleResourceDto roleResourceDto = rolePermission.getRoleResource();
        List<ResourceDto> resourceDtoList = roleResourceDto.getResourceDtoList();
        Map<String, RoleColumn> roleColumnMap = new HashMap<>();
        //List<ResourceDto> resourceDtoList = roleResourceDtoList.
        resourceDtoList.forEach(rd->{
            //提取一个RoleResource
            RoleResource roleResource = new RoleResource(roleResourceDto.getRoleId(), rd.getId());
            allRoleResourceList.add(roleResource);
            if(rd.getStatus() != null && rd.getStatus() == 1) {
                roleResourceList.add(roleResource);
                rd.getColumnDtoList().forEach(cd->{
                    cd.getColumns().forEach(c->{
                        //提取一个RoleColumn
                        String key = roleResourceDto.getRoleId() + "-" + c.getTableName() + "-"  + c.getId();
                        if(roleColumnMap.get(key) != null) {
                            return;
                        }
                        RoleColumn roleColumn = new RoleColumn(roleResourceDto.getRoleId(), c.getTableName(), c.getId(), c.getColumnFiled(), c.getColumnName());
                        roleColumn.setRoleName(rolePermission.getRoleName());

                        if(c.getStatus() != null && c.getStatus() == 1) {
                            allRoleColumnList.add(roleColumn);
                            roleColumnList.add(roleColumn);
                        }
//
//                            roleColumnDeleteList.add(roleColumn);
                        roleColumnMap.put(key, roleColumn);
                    });
                });
            }
            roleResourceDeleteList.add(roleResource);
        });

        roleService.auditPermission(roleResourceDto.getRoleId(), roleColumnList, roleResourceList);
        return true;
    }
}
