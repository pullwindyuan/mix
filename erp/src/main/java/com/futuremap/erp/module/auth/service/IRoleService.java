package com.futuremap.erp.module.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.erp.module.auth.dto.RoleDetailDto;
import com.futuremap.erp.module.auth.dto.RoleDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.RoleResource;

import java.util.List;

/**
 * <p>
 * 授权角色表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
public interface IRoleService extends IService<Role> {
    List<Role> findList(Role role);
    List<RoleDetailDto> findDetailList(Role role);
    List<RoleDetailDto> findAuditDetailList(Role role);
    Integer updatePermission(Integer roleId, List<RoleColumn> roleColumnList, List<RoleResource> roleResourceList);

    Integer auditPermission(Integer roleId, List<RoleColumn> roleColumnList, List<RoleResource> roleResourceList);

    boolean add(List<Role> Roles);

    boolean delete(List<Role> Roles);

    boolean update(List<Role> Roles);
}
