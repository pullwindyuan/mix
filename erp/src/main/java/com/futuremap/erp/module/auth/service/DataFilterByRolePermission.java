package com.futuremap.erp.module.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futuremap.erp.module.auth.dto.ResourceDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.futuremap.erp.module.auth.service.impl.*;
import com.futuremap.erp.module.saleorder.dto.SaleOrderDto;
import com.futuremap.erp.utils.GeneralUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFilterByRolePermission {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RoleColumnServiceImpl roleColumnService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Autowired
    private RoleResourceServiceImpl roleResourceService;

    User user;

    JSONObject userObj;

    Integer roleId;

    Role role;

    Map<String, Integer> userColumn;

    JSONObject rule;

    List<ResourceDto> resourceDtoList;

    public void initRoleUserColumnPermission(String tableName) {
        user = GeneralUtils.getUser(userServiceImpl);
        userObj = (JSONObject) JSONObject.toJSON(user);
        roleId = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id",user.getId())).getRoleId();
        role = roleService.getOne(new QueryWrapper<Role>().eq("id",roleId));
        userColumn  = roleColumnService.getColumnDtoByUser(user, tableName);
        //{"companyCode":"companyCode","cpersoncode":"code"}
        rule = JSONObject.parseObject(role.getDetail());
    }

    public void initRoleUserWritePermission() {
        user = GeneralUtils.getUser(userServiceImpl);
        userObj = (JSONObject) JSONObject.toJSON(user);
        roleId = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id",user.getId())).getRoleId();
        RoleResource roleResource =new RoleResource();
        roleResource.setRoleId(roleId);
        List<Role> roleIds = new ArrayList<>();
        Role role = new Role();
        role.setId(roleId);
        roleIds.add(role);
        List<RoleResourceDto> roleResourceDtoList = roleResourceService.findListByRoleIds(roleIds, false);
        if(roleResourceDtoList != null && roleResourceDtoList.size() > 0) {
            resourceDtoList = roleResourceDtoList.get(0).getResourceDtoList();
        }else {
            resourceDtoList = null;
        }

    }

    public boolean checkWritePermission(HttpServletRequest request) {
        initRoleUserWritePermission();
        if(resourceDtoList == null || resourceDtoList.size() == 0) {
            return false;
        }
        for (ResourceDto r : resourceDtoList) {
            if(r.getType() == 3 && r.getStatus() == 1 && request.getRequestURL().indexOf(r.getUrl()) > 0) {
                return true;
            }
        }

        return false;
    }

    public <T> void rowFilter(T bean) {
            Field[] fs = bean.getClass().getDeclaredFields();

            Map<String, Field> fsMap = new HashMap<>();
            for (Field f : fs) {
                fsMap.put(f.getName(), f);
            }

            //{"companyCode":"companyCode","cpersoncode":"code"}
        if(rule != null) {
            rule.forEach((k, v) -> {
                Field beanField = fsMap.get(k);
                if (beanField != null) {
                    beanField.setAccessible(true);
                    String value = userObj.getString((String) v);
                    if (StringUtils.isNotBlank(value) && !value.equals("all")) {
                        try {
                            beanField.set(bean, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public <T> void columnFilter(T bean) throws IllegalAccessException {
            Field[] fs = bean.getClass().getDeclaredFields();

            for (Field field : fs) {
                field.setAccessible(true);
                Integer status = userColumn.get(field.getName());
                if(status != null && status == 0) {
                    field.set(bean, null);
                }
            }
    }

    public void columnFilter(List<SaleOrderDto> saleOrderDtoList) {
        saleOrderDtoList.forEach(e -> {
            try {
                columnFilter(e);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });
    }
}
