package com.futuremap.erp.module.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.futuremap.erp.module.auth.dto.ColumnDto;
import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.*;
import com.futuremap.erp.module.auth.mapper.RoleColumnMapper;
import com.futuremap.erp.module.auth.service.IRoleColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
public class RoleColumnServiceImpl extends ServiceImpl<RoleColumnMapper, RoleColumn> implements IRoleColumnService {
    private  static Map<Integer, RoleColumnDto> roleColumnDtoMap = new HashMap<>();
    private  static Map<Integer, Map<String, Integer>> userColumnMap = new HashMap<>();

    @Autowired
    private RoleColumnMapper roleColumnMapper;
    @Autowired
    private  UserRoleServiceImpl userRoleService;
    @Autowired
    private  RoleResourceServiceImpl roleResourceService;
    @Override
    public List<RoleColumnDto> findList(RoleColumn roleColumn) {
//        List<RoleColumn> list = roleColumnMapper.findList(roleColumn);
//        return RoleColumnDto.createRoleColumnDtoList(list);
        return null;
    }

    @Override
    public List<RoleColumnDto> findListByRoleIds(@Param("list")List<Integer> list, @Param("isAudit") Boolean isAudit) {
        List<RoleColumn> roleColumnList = roleColumnMapper.findListByRoleIds(list, isAudit);
        return RoleColumnDto.createRoleColumnDtoList(roleColumnList);
    }
    @Override
    public boolean addPermission(List<RoleColumn> roleColumns) {
        return saveBatch(roleColumns);
    }

    @Override
    public boolean deletePermission(List<RoleColumn> roleColumns) {
        return removeByIds(roleColumns);
    }

    @Override
    public boolean updatePermission(List<RoleColumn> roleColumns) {
        return saveOrUpdateBatch(roleColumns);
    }

    public Map<String, Integer> getColumnDtoByUser(User user, String tableName) {
        Integer roleId = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id",user.getId())).getRoleId();
        Map<String, Integer> columnMap = userColumnMap.get(roleId);
        RoleColumnDto roleColumnDto = null;//roleColumnDtoMap.get(roleId);
        if(columnMap == null) {
            columnMap = new HashMap<>();
            final ColumnDto[] columnDto = new ColumnDto[1];
            if (roleColumnDto == null) {
                List<Role> roleIdList = new ArrayList<>();
                roleIdList.add(new Role().setId(roleId));
                List<RoleResourceDto> roleResourceDtoList = roleResourceService.findListByRoleIds(roleIdList, false);
                Map<String, Integer> finalColumnMap = columnMap;
                roleResourceDtoList.forEach(rr-> {
                    rr.getResourceDtoList().forEach(rd -> {
                        rd.getColumnDtoList().forEach(cd -> {
                            if(cd.getTableName().equals(tableName)) {
                                cd.getColumns().forEach(e->{
                                    finalColumnMap.put(e.getColumnFiled(), e.getStatus());
                                });
                            }
                        });
                    });
                });
            }
        }
        return columnMap;
    }
}
