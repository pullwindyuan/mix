package com.futuremap.erp.module.auth.service;

import com.futuremap.erp.module.auth.dto.ResourceDto;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.erp.module.auth.entity.RoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
public interface IResourceService extends IService<Resource> {
    List<ResourceDto> findList(Resource resource);
    List<ResourceDto> findListByResourceIdsAndRoleIds(List<Integer> resourceIds, List<Integer> roleIds, Boolean isAudit);
    Map<Integer,ResourceDto> findMapByResourceIdsAndRoleIds(List<Integer> resourceIds, List<Integer> roleIds, Boolean isAudit);
    List<ResourceDto> findListByResourceIds(Map<String, RoleResource> roleResourceMap,List<Integer> resourceIds);
}
