package com.futuremap.erp.module.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.erp.module.auth.dto.RoleDto;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.RoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 授权角色表 Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-19
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findList(@Param("role") Role role);
    List<Role> findAuditList(@Param("role") Role role);
    Integer updatePermission(@Param("roleId") Integer roleId, @Param("roleColumnList")List<RoleColumn> roleColumnList, @Param("roleResourceList")List<RoleResource> roleResourceList);

    Integer auditPermission(@Param("roleId") Integer roleId, @Param("roleColumnList")List<RoleColumn> roleColumnList, @Param("roleResourceList")List<RoleResource> roleResourceList);
}
