package com.futuremap.erp.module.auth.mapper;

import com.futuremap.erp.module.auth.entity.RoleColumn;
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
public interface RoleColumnMapper extends BaseMapper<RoleColumn> {
    List<RoleColumn> findList(@Param("roleColumn") RoleColumn roleColumn);
    List<RoleColumn> findListByRoleIds(@Param("list") List<Integer> list, @Param("isAudit") Boolean isAudit);

}
