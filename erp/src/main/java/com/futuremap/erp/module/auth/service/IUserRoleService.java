package com.futuremap.erp.module.auth.service;

import com.futuremap.erp.module.auth.dto.UserRoleDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 授权用户角色 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
public interface IUserRoleService extends IService<UserRole> {
    List<UserRole> findList(UserRole userRole);
    List<UserRoleDto> findDetailList(UserRole userRole);
}
