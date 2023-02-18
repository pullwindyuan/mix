package com.futuremap.erp.module.auth.service.impl;

import com.futuremap.erp.module.auth.dto.ResourceDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.mapper.RoleResourceMapper;
import com.futuremap.erp.module.auth.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.utils.BeanUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 授权角色功能表 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private ResourceServiceImpl resourceService;
    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public List<RoleResourceDto> findList(RoleResource roleResource) {
        List<RoleResource> roleResourceList = roleResourceMapper.findList(roleResource);

        Map<Integer, List<Integer>> roleResourceIdMap = new HashMap<>();
        Map<String, RoleResource> roleResourceMap = new HashMap<>();
        roleResourceList.forEach(r->{
            List<Integer> temp = roleResourceIdMap.get(r.getRoleId());
            if(temp == null) {
                temp = new ArrayList<>(r.getResourceId());
                roleResourceIdMap.put(r.getRoleId(), temp);
            }else {
                temp.add(r.getResourceId());
            }
            roleResourceMap.put(r.getRoleId() + "-" + r.getResourceId(), r);
        });

        List<RoleResourceDto> resourceDtoList = new ArrayList<>();
        roleResourceIdMap.forEach((k,s)->{
            resourceDtoList.add(new RoleResourceDto(k, roleResourceMap.get(k).getRoleName(),resourceService.findListByResourceIds(roleResourceMap, s)));
        });
        return resourceDtoList;
    }

    @Override
    public List<RoleResourceDto> findListByRoleIds(List<Role> roleList, Boolean isAudit) {
        List<Integer> ids = new ArrayList<>();
        roleList.forEach(r->{
            ids.add(r.getId());
        });
        List<RoleResource> roleResourceList = roleResourceMapper.findListByRoleIds(ids, isAudit);
        if(roleResourceList .size() == 0 && isAudit == true) {
            return new ArrayList<>();
        }
        //获取全部菜单资源列表
        Resource resourceQ = new Resource();
        //resourceQ.setLevel(0);
        //获取系统存在的所有菜单资源数据
        List<ResourceDto> allResourceDtoList = resourceService.findList(resourceQ);

        Map<Integer, List<Integer>> roleResourceIdMap = new HashMap<>();
        Map<Integer, String> roleNameMap = new HashMap<>();
        Map<String, RoleResource> roleResourceMap = new HashMap<>();
        roleResourceList.forEach(r->{
            roleResourceMap.put(r.getRoleId() + "-" + r.getResourceId(), r);
        });
        //如果没有任何角色授权数据，就默认把所有的菜单和列信息返回给客户端，所以需要生成一份全量的菜单和字段数据
        List<RoleResource> defaultRoleResources = createDefaultRoleResources(roleList, allResourceDtoList);
        defaultRoleResources.forEach(drs->{
            //找到已授权的角色信息，然后把默认信息替换成授权信息,这样就完成了默认数据和授权数据的聚合
            RoleResource rr = roleResourceMap.get(drs.getRoleId() + "-" + drs.getResourceId());
            if(rr != null) {
                drs.setStatus(1);
                drs.setId(rr.getId());
            }
        });

        defaultRoleResources.forEach(r->{
            List<Integer> temp = roleResourceIdMap.get(r.getRoleId());
            if(temp == null) {
                temp = new ArrayList<>();
                temp.add(r.getResourceId());
                roleResourceIdMap.put(r.getRoleId(), temp);
            }else {
                temp.add(r.getResourceId());
            }
            roleNameMap.put(r.getRoleId(), r.getRoleName());
        });
        //聚合全部资源数据和已授权资源数据
        List<RoleResourceDto> roleResourceDtoList = new ArrayList<>();
        roleResourceIdMap.forEach((k,s)->{
            //将角色的授权状态聚合到所有菜单信息当中，便于前端渲染
            Map<Integer, ResourceDto> resourceDtoMap = new HashMap<>();
            List<ResourceDto> tempResourceDtoList = BeanUtil.copyProperties(allResourceDtoList, ResourceDto.class);
            tempResourceDtoList.forEach(cr->{
                cr.setStatus(0);
                resourceDtoMap.put(cr.getId(), cr);
            });
            //Map<Integer, ResourceDto> currResourceDtoMap = resourceService.findMapByResourceIdsAndRoleIds(s, roleIds);
            List<ResourceDto> currResourceDtoList = resourceService.findListByResourceIdsAndRoleIds(s, ids, isAudit);
            currResourceDtoList.forEach(cr->{
                //使用用户实际授权菜单的数据覆盖全量数据对应的部分
                cr.setStatus(1);
                //resourceDtoMap.put(cr.getId(),cr);
                if(roleResourceMap.get(k + "-" + cr.getId()) != null) {
                    resourceDtoMap.get(cr.getId()).setStatus(1);
                }
                resourceDtoMap.get(cr.getId()).setColumnDtoList(cr.getColumnDtoList());
            });
            RoleResourceDto roleResourceDto = new RoleResourceDto(k, roleNameMap.get(k), tempResourceDtoList);
            roleResourceDtoList.add(roleResourceDto);
        });
        return roleResourceDtoList;
    }

    private List<RoleResource> createDefaultRoleResources(List<Role> roleList, List<ResourceDto> resourceDtoList) {
        List<RoleResource> roleResourceList = new ArrayList<>();
        resourceDtoList.forEach(r->{
            roleList.forEach(role->{
                roleResourceList.add(new RoleResource(role.getId(), r.getId(), role.getRoleName()));
            });
        });
        return roleResourceList;
    }

    private Map<String, RoleResource> createDefaultRoleResourceMap(List<ResourceDto> resourceDtoList) {
        Map<String, RoleResource> roleResourceMap = new HashMap<>();
        List<Role> roleList = roleService.findList(new Role());
        resourceDtoList.forEach(r->{
            roleList.forEach(role->{
                roleResourceMap.put(role.getId() + "-" +  r.getId(), new RoleResource(role.getId(), r.getId(), role.getRoleName()));
            });
        });
        return roleResourceMap;
    }

    private List<RoleResource> createDefaultRoleResourcesFromResources(List<Resource> resourceList) {
        List<RoleResource> roleResourceList = new ArrayList<>();
        List<Role> roleList = roleService.findList(new Role());
        resourceList.forEach(r->{
            roleList.forEach(role->{
                roleResourceList.add(new RoleResource(role.getId(), r.getId(), role.getRoleName()));
            });
        });
        return roleResourceList;
    }

    @Override
    public boolean addPermission(List<RoleResource> roleResources) {
        return saveBatch(roleResources);
    }

    @Override
    public boolean deletePermission(List<RoleResource> roleResources) {
        return removeByIds(roleResources);
    }

    @Override
    public boolean updatePermission(List<RoleResource> roleResources) {
        return saveOrUpdateBatch(roleResources);
    }
}
