package com.futuremap.erp.module.auth.dto;

import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能表
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleColumnDto对象", description="角色与表字段权限")
public class RoleColumnDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 父（角色）id
     */
    @ApiModelProperty(value = "父（角色）id")
    private Integer roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 列信息
     */
    @ApiModelProperty(value = "列信息")
    private List<ColumnDto> columnDTOs;

    public void addColumnDto(ColumnDto columnDto) {
        if(columnDTOs == null) {
            columnDTOs = new ArrayList<>();
        }
        columnDTOs.add(columnDto);
    }

    public RoleColumnDto() {
        super();
    }

    public RoleColumnDto(RoleColumn roleColumn) {
        super();
        this.setRoleId(roleColumn.getRoleId());
        this.addColumnDto(roleColumn.getColumnDto());
    }
    public static List<RoleColumnDto> createRoleColumnDtoList(List<RoleColumn> roleColumnList) {
        Map<Integer, RoleColumnDto> roleColumnDtoMap = new HashMap<>();
        Map<String,ColumnDto> columnDtoMap = new HashMap<>();
        roleColumnList.forEach(e->{
            Integer roleId = e.getRoleId();
            RoleColumnDto roleColumnDto = roleColumnDtoMap.get(roleId);
            if(roleColumnDto == null) {
                roleColumnDto = new RoleColumnDto(e);
                roleColumnDto.setRoleId(e.getRoleId());
                roleColumnDto.setRoleName(e.getRoleName());
                columnDtoMap.put(e.getTableName(), roleColumnDto.columnDTOs.get(0));
                roleColumnDtoMap.put(roleId, roleColumnDto);
            }
            ColumnDto columnDto = e.getColumnDto();
            String tableName = columnDto.getTableName();
            ColumnDto columnDtoTemp = columnDtoMap.get(tableName);
            if(columnDtoTemp == null) {
                columnDtoMap.put(tableName, columnDto);
                roleColumnDto.addColumnDto(columnDto);
                return;
            }
            columnDtoTemp.getColumns().addAll(columnDto.getColumns());

        });
        List<RoleColumnDto> list = new ArrayList<>();
        roleColumnDtoMap.forEach((k,e)->{
            list.add(e);
        });
        return list;
    }
}
