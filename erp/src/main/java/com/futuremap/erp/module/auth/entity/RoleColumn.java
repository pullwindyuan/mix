package com.futuremap.erp.module.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.futuremap.erp.module.auth.dto.ColumnDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.futuremap.erp.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授权角色功能表
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_role_column")
@ApiModel(value="RoleColumn对象", description="授权角色功能表")
public class RoleColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 列id
     */
    @ApiModelProperty(value = "列id")
    private Integer columnId;

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
     * 表名
     */
    @ApiModelProperty(value = "表名")
    @TableField(exist=false)
    private String tableName;

    /**
     * 列字段
     */
    @ApiModelProperty(value = "列字段")
    @TableField(exist=false)
    private String columnFiled;

    /**
     * 列名
     */
    @ApiModelProperty(value = "列名")
    @TableField(exist=false)
    private String columnName;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Integer createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Integer updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatedAt;

    /**
     * 物理删除状态 （0:正常;1:删除）
     */
    @ApiModelProperty(value = "物理删除状态 （0:正常;1:删除）")
    private Integer delStatus;

    public ColumnDto getColumnDto() {
        return  new ColumnDto(getColumn());
    }

    public Column getColumn() {
        Column column = new Column();
        column.setId(this.getColumnId());
        column.setRoleColumnId(this.id);
        column.setTableName(tableName);
        column.setColumnFiled(columnFiled);
        column.setColumnName(columnName);
        return column;
    }

    public RoleColumn() {
        super();
    }
    public RoleColumn(Integer roleId, String tableName, Integer columnId, String columnFiled, String columnName) {
        super();
        this.roleId = roleId;
        this.tableName = tableName;
        this.columnId = columnId;
        this.columnFiled = columnFiled;
        this.columnName = columnName;
    }
}
