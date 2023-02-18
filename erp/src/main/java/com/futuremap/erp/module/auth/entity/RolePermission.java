package com.futuremap.erp.module.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.futuremap.erp.module.auth.dto.RoleResourceDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 授权角色表
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="角色权限对象", description="角色权限对象")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;
//    /**
//     * 父（角色）id
//     */
//    @ApiModelProperty(value = "父（角色）id")
//    private Integer roleId;
    /**
     * 角色菜单权限,包括字段权限
     */
    @ApiModelProperty(value = "角色菜单权限,包括字段权限")
    private List<RoleResourceDto> roleResourceDtoList;

}
