package com.futuremap.erp.module.auth.service;

import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.erp.module.auth.entity.RoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 授权角色功能表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
public interface IRoleColumnService extends IService<RoleColumn> {
    List<RoleColumnDto> findList(@Param("roleColumn") RoleColumn roleColumn);
    List<RoleColumnDto> findListByRoleIds(@Param("list")List<Integer> list, @Param("isAudit") Boolean isAudit);
    boolean addPermission(List<RoleColumn> roleColumns);

    boolean deletePermission(List<RoleColumn> roleColumns);

    boolean updatePermission(List<RoleColumn> roleColumns);
}
