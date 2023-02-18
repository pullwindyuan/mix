package com.futuremap.erp.module.auth.dto;

import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.RoleResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@ApiModel(value="用户资源才做权限对象", description="用户资源才做权限对象")
public class RoleResourceDto implements Serializable {

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
     * 资源信息列表
     */
    @ApiModelProperty(value = "资源信息列表")
    private List<ResourceDto> resourceDtoList;

    public void addResourceDto(ResourceDto resourceDto) {
        if(resourceDtoList == null) {
            resourceDtoList = new ArrayList<>();
        }
        resourceDtoList.add(resourceDto);
    }
    public RoleResourceDto() {
        super();
    }

    public RoleResourceDto(Integer roleId, String roleName, List<ResourceDto> resourceDtoList) {
        super();
        this.setRoleId(roleId);
        this.setRoleName(roleName);
        this.resourceDtoList = resourceDtoList;
    }
}
