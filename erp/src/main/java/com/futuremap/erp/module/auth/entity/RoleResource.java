package com.futuremap.erp.module.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.futuremap.erp.module.auth.dto.ResourceDto;
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
@TableName("auth_role_resource")
@ApiModel(value="RoleResource对象", description="授权角色功能表")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 功能id
     */
    @ApiModelProperty(value = "功能id")
    private Integer resourceId;

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

    public RoleResource() {
        super();
    }

    public RoleResource(Integer roleId, Integer resourceId) {
        super();
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public RoleResource(Integer roleId, Integer resourceId, String roleName) {
        super();
        this.status = 0;
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.setRoleName(roleName);
    }

}
