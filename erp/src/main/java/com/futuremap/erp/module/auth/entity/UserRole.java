package com.futuremap.erp.module.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.futuremap.erp.module.auth.dto.RoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.futuremap.erp.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 授权用户角色
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_user_role")
@ApiModel(value="UserRole对象", description="授权用户角色")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 物理删除状态 （0:正常;1:删除）
     */
    @ApiModelProperty(value = "物理删除状态 （0:正常;1:删除）")
    private Integer delStatus;


}
