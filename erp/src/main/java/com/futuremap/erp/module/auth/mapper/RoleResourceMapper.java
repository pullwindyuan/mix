package com.futuremap.erp.module.auth.mapper;

import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 授权角色功能表 Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-22
*/
@Mapper
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    List<RoleResource> findList(@Param("roleResource")RoleResource roleResource);
    List<RoleResource> findListByRoleIds(@Param("list")List<Integer> list, @Param("isAudit") Boolean isAudit);
}
