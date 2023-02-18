package com.futuremap.erp.module.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.dto.RoleDetailDto;
import com.futuremap.erp.module.auth.dto.RoleDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.mapper.RoleMapper;
import com.futuremap.erp.module.auth.service.IRoleService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 授权角色表 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleResourceServiceImpl roleResourceService;
    @Override
    public List<Role> findList(Role role) {
        return baseMapper.findList(role);
    }
    StringBuffer ids = new StringBuffer();
    @Override
    public List<RoleDetailDto> findDetailList(Role role) {
        List<Role> roleList = baseMapper.findList(role);
        List<RoleResourceDto> roleResourceList = roleResourceService.findListByRoleIds(roleList, false);
        return RoleDetailDto.createRoleDetailDtoList(roleList, roleResourceList);
    }

    @Override
    public List<RoleDetailDto> findAuditDetailList(Role role) {
        List<Role> roleList = baseMapper.findAuditList(role);
        //这里只获取需要审核的角色信息
        List<RoleResourceDto> roleResourceList = roleResourceService.findListByRoleIds(roleList, true);
        return RoleDetailDto.createRoleDetailDtoList(roleList, roleResourceList);
    }

    @Override
    public Integer updatePermission(Integer roleId, List<RoleColumn> roleColumnList, List<RoleResource> roleResourceList) {
        return baseMapper.updatePermission(roleId, roleColumnList, roleResourceList);
    }

    @Override
    public Integer auditPermission(Integer roleId, List<RoleColumn> roleColumnList, List<RoleResource> roleResourceList) {
        return baseMapper.auditPermission(roleId, roleColumnList, roleResourceList);
    }

    @Override
    public boolean add(List<Role> Roles) {
        return saveBatch(Roles);
    }

    @Override
    public boolean delete(List<Role> Roles) {
        List<Integer> ids = new ArrayList<>();
        Roles.forEach(r->{
            ids.add(r.getId());
        });
        return removeByIds(ids);
    }

    @Override
    public boolean update(List<Role> Roles) {
        return updateBatchById(Roles);
    }
}
