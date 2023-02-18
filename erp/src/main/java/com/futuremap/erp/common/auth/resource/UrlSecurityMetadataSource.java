/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.common.auth.resource;

import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futuremap.erp.common.Constants;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.mapper.ResourceMapper;
import com.futuremap.erp.module.auth.mapper.RoleMapper;
import com.futuremap.erp.module.auth.mapper.RoleResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @title UrlFilterInvocationSecurityMetadataSource
 * @description TODO
 * @date 2021/6/24 10:23
 */
public class UrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private UrlDynamicSecurityServiceImpl dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    RoleResourceMapper roleResourceMapper;
    @Autowired
    RoleMapper roleMapper;

    /***
     * 返回该url所需要的用户权限信息
     *
     * @param object: 储存请求url信息
     * @return: null：标识不需要任何权限都可以访问
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取所有URL
        if (configAttributeMap == null) {
            this.loadDataSource();
        }
        //获取当前访问的路径
        String url = ((FilterInvocation) object).getRequestUrl();
        String path = URLUtil.getPath(url);
        System.out.println(path);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        List<String> roles = new LinkedList<>();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            System.out.println(pattern + "----------" + path);
            if (pathMatcher.match(pattern, path)) {
                List<RoleResource> resources = roleResourceMapper.selectList(new QueryWrapper<RoleResource>().eq("resource_id", configAttributeMap.get(pattern).getAttribute()));
                roles = resources.stream().map(RoleResource::getRoleName).collect(Collectors.toList());
                // 保存该url对应角色权限信息
                return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
            }
        }
        // 如果数据中没有找到相应url资源则为非法访问，要求用户登录再进行操作
        return SecurityConfig.createList(Constants.ROLE_LOGIN);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
