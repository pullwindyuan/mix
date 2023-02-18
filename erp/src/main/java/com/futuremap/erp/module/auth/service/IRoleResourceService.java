package com.futuremap.erp.module.auth.service;

import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 授权角色功能表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
public interface IRoleResourceService extends IService<RoleResource> {
    List<RoleResourceDto> findList(RoleResource roleResource);
    List<RoleResourceDto> findListByRoleIds(List<Role> roleList, Boolean isAudit);
    boolean addPermission(List<RoleResource> roleResources);

    boolean deletePermission(List<RoleResource> roleResources);

    boolean updatePermission(List<RoleResource> roleResources);
}
