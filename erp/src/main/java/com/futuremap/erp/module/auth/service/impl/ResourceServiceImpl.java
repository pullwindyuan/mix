package com.futuremap.erp.module.auth.service.impl;

import com.futuremap.erp.module.auth.dto.ColumnDto;
import com.futuremap.erp.module.auth.dto.ResourceDto;
import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import com.futuremap.erp.module.auth.entity.*;
import com.futuremap.erp.module.auth.mapper.ResourceMapper;
import com.futuremap.erp.module.auth.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.utils.BeanUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能表 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ColumnServiceImpl columnService;
    @Autowired
    RoleColumnServiceImpl roleColumnService;
    @Override
    public List<ResourceDto> findList(Resource resource) {
        List<Resource> list = resourceMapper.findList(resource);
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        list.forEach(r->{
            List<ColumnDto> columnsDto =  columnService.findListByTables(Constants.RESOURCE_TABLES.get(r.getName()));
            ResourceDto resourceDto = BeanUtil.copyProperties(r, ResourceDto.class);
            resourceDto.setColumnDtoList(columnsDto);
            resourceDtoList.add(resourceDto);
        });


        return resourceDtoList;
    }

    @Override
    public List<ResourceDto> findListByResourceIdsAndRoleIds(List<Integer> resourceIds, List<Integer> roleIds, Boolean isAudit) {
        List<Resource> list = resourceMapper.findListByResourceIds(resourceIds);
        List<ResourceDto> resourceDtoList = new ArrayList<>();

        list.forEach(r->{
            String tablesName = Constants.RESOURCE_TABLES.get(r.getName());
            //这是获取的角色授权菜单对应的全部表的全部列（包括未授权的）数据
            List<ColumnDto> allColumnsDtoList =  columnService.findListByTables(Constants.RESOURCE_TABLES.get(r.getName()));
            //这是获取的角色授权菜单对应的全部表的角色授权列数据
            List<RoleColumnDto> roleColumnDtoList =  roleColumnService.findListByRoleIds(roleIds, isAudit);
            //聚合全部列和授权列，便于前端渲染
            Map<String, ColumnDto> columnDtoMap = new HashMap<>();
            //如果角色还不存在任何列授权信息，就需要默认把对应菜单的所有数据列全部返回给客户端
            Map<String, Column> columnMap = new HashMap<>();
            Map<String, ColumnDto> currColumnDtoMap = new HashMap<>();
            allColumnsDtoList.forEach(acd->{
                //遍历全部列
                List<Column> allColumnList = acd.getColumns();
                allColumnList.forEach(iterm->{
                    iterm.setStatus(0);
                    columnMap.put(iterm.getTableName() + "-" + iterm.getColumnFiled(), iterm);
                });
                columnDtoMap.put(acd.getTableName(), acd);
                currColumnDtoMap.put(acd.getTableName(), acd);
            });

            //遍历授权表
            if(roleColumnDtoList.size() > 0) {
                roleColumnDtoList.forEach(rc -> {
                    List<ColumnDto> currColumnDtoList = rc.getColumnDTOs();
                    currColumnDtoList.forEach(t -> {
                        t.getColumns().forEach(column -> {
                            if (columnMap.get(column.getTableName() + "-" + column.getColumnFiled()) != null) {
                                columnMap.get(column.getTableName() + "-" + column.getColumnFiled()).setStatus(1);
                            }
                        });
                    });
                    //roleColumnDtoMap.put(rc.getRoleId(),rc);
                });
            }else {
                allColumnsDtoList.forEach(c -> {
                    //遍历授权列
                    List<Column> columnList = c.getColumns();
                    columnList.forEach(curr -> {
                        columnMap.get(curr.getTableName() + "-" + curr.getColumnFiled()).setStatus(1);
                    });
                });
            }
            //currColumnDtoMap已经存储了聚合后的角色权限对应的列权限数据，所以遍历组织成一个数据即可
            ResourceDto rd = BeanUtil.copyProperties(r, ResourceDto.class);
            List<ColumnDto> columnDtoList = new ArrayList<>();
            //组装List<ColumnDto>

            currColumnDtoMap.forEach((k,v)->{
                columnDtoList.add(v);
            });
            rd.setColumnDtoList(columnDtoList);
            resourceDtoList.add(rd);;
        });
        return resourceDtoList;
    }

    @Override
    public Map<Integer,ResourceDto> findMapByResourceIdsAndRoleIds(List<Integer> resourceIds, List<Integer> roleIds, Boolean isAudit) {
        List<Resource> list = resourceMapper.findListByResourceIds(resourceIds);
        Map<Integer,ResourceDto> resourceDtoList = new HashMap<>();
        list.forEach(r->{
            String tablesName = Constants.RESOURCE_TABLES.get(r.getName());
            //这是获取的角色授权菜单对应的全部表的全部列（包括未授权的）数据
            List<ColumnDto> allColumnsDtoList =  columnService.findListByTables(Constants.RESOURCE_TABLES.get(r.getName()));
            //这是获取的角色授权菜单对应的全部表的角色授权列数据
            List<RoleColumnDto> roleColumnDtoList =  roleColumnService.findListByRoleIds(roleIds, isAudit);
            //聚合全部列和授权列，便于前端渲染
            Map<String, ColumnDto> columnDtoMap = new HashMap<>();
            //拷贝一份出来备用
            //List<RoleColumnDto> tempResourceDtoList = BeanUtil.copyProperties(allColumnsDtoList, RoleColumnDto.class);
            roleColumnDtoList.forEach(rc->{
                List<ColumnDto> allColumnDtoList = rc.getColumnDTOs();
                allColumnDtoList.forEach(t->{
                    columnDtoMap.put(t.getTableName(), t);
                });
            });
            //遍历授权表
            Map<String, ColumnDto> currColumnDtoMap = new HashMap<>();
            allColumnsDtoList.forEach(c->{
                //拷贝一份备用
                ColumnDto columnDtoTemp = columnDtoMap.get(c.getTableName());
                ColumnDto columnDto = BeanUtil.copyProperties(columnDtoTemp, ColumnDto.class);
                //复制后保存到MAP中，便于后面通过表名称查找
                currColumnDtoMap.put(columnDto.getTableName(), columnDto);
                //遍历全部列
                List<Column> allColumnList = columnDto.getColumns();
                Map<String, Column> columnMap = new HashMap<>();
                allColumnList.forEach(all->{
                    all.setStatus(0);
                    columnMap.put(all.getColumnFiled(), all);
                });
                //遍历授权列
                List<Column> columnList = c.getColumns();
                columnList.forEach(curr->{
                    columnMap.get(curr.getColumnFiled()).setStatus(1);
                });
            });
            //currColumnDtoMap已经存储了聚合后的角色权限对应的列权限数据，所以遍历组织成一个数据即可
            ResourceDto rd = BeanUtil.copyProperties(r, ResourceDto.class);
            List<ColumnDto> columnDtoList = new ArrayList<>();
            //组装List<ColumnDto>
            currColumnDtoMap.forEach((k,v)->{
                //if(tablesName.contains(v.getTableName())) {
                columnDtoList.add(v);
                //}

            });
            rd.setColumnDtoList(columnDtoList);
            resourceDtoList.put(rd.getId(),rd);;
        });
        return resourceDtoList;
    }

    @Override
    public List<ResourceDto> findListByResourceIds(Map<String, RoleResource> roleResourceMap, List<Integer> resourceIds) {
        List<Resource> list = resourceMapper.findListByResourceIds(resourceIds);
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        list.forEach(r->{
            String name = Constants.RESOURCE_TABLES.get(r.getName());

            List<ColumnDto> columnsDto =  columnService.findListByTables(Constants.RESOURCE_TABLES.get(r.getName()));
            ResourceDto resourceDto = BeanUtil.copyProperties(r, ResourceDto.class);
            //暂时用不上
            //resourceDto.setRoleResourceId(roleResourceMap.get(resourceDto));
            resourceDto.setColumnDtoList(columnsDto);
            resourceDtoList.add(resourceDto);
        });
        return resourceDtoList;
    }
}
