package com.futuremap.erp.module.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.futuremap.erp.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("auth_column")
@ApiModel(value="Column对象", description="功能表")
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色与列关联ID
     */
    @ApiModelProperty(value = "角色与列关联ID")
    @TableField(exist = false)
    private Integer roleColumnId;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableName;

    /**
     * 列字段
     */
    @ApiModelProperty(value = "列字段")
    private String columnFiled;

    /**
     * 列名
     */
    @ApiModelProperty(value = "列名")
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

    /**
     * 授权状态
     */
    @ApiModelProperty(value = "授权状态：0未授权，1已授权")
    @TableField(exist = false)
    private Integer status;
}
