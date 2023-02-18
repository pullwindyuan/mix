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
@TableName("auth_resource")
@ApiModel(value="Resource对象", description="功能表")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色与菜单关联ID
     */
    @ApiModelProperty(value = "角色与菜单关联ID")
    @TableField(exist = false)
    private Integer roleResourceId;

    /**
     * 类型 0 菜单 1 菜单接口一体 2 读数据接口 3 写数据接口
     */
    @ApiModelProperty(value = "类型 0 菜单 1 菜单接口一体 2 读数据接口 3 写数据接口")
    private Integer type;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer parentId;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String name;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private Integer level;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String url;

    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名")
    private String methodName;

    /**
     * 方法类型
     */
    @ApiModelProperty(value = "方法类型")
    private String methodType;

    /**
     * 授权状态
     */
    @ApiModelProperty(value = "授权状态：0表示未授权，1表示已授权")
    @TableField(exist = false)
    private Integer status;

//    /**
//     * 关联的表名称
//     */
//    @ApiModelProperty(value = "关联的表名称")
//    private String tables;

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


}
