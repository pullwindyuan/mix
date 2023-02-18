package com.futuremap.erp.module.auth.service.impl;

import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.dto.RoleDetailDto;
import com.futuremap.erp.module.auth.dto.RoleDto;
import com.futuremap.erp.module.auth.dto.UserRoleDto;
import com.futuremap.erp.module.auth.entity.*;
import com.futuremap.erp.module.auth.mapper.RoleMapper;
import com.futuremap.erp.module.auth.mapper.UserRoleMapper;
import com.futuremap.erp.module.auth.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 授权用户角色 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleServiceImpl roleService;
    @Override
    public List<UserRole> findList(UserRole userRole) {
        return userRoleMapper.findList(userRole);
    }

    @Override
    public List<UserRoleDto> findDetailList(UserRole userRole) {
        List<UserRole> userRoleList = userRoleMapper.findList(userRole);

        Role role = new Role();
        role.setId(userRole.getRoleId());
        List<RoleDetailDto> roleDtoList = roleService.findDetailList(role);

        return UserRoleDto.createUserRoleDtoList(userRoleList, roleDtoList);
    }

}
