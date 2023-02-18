package com.futuremap.erp.module.auth.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.model.BaseEntity;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@ApiModel(value="RoleDetailDto对象", description="授权角色详情信息")
public class RoleDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 角色详情
     */
    @ApiModelProperty(value = "角色详情")
    private String detail;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 角色授权菜单列表
     */
    @ApiModelProperty(value = "角色授权菜单列表")
    private RoleResourceDto roleResource;


    /**
     * 物理删除状态 （0:正常;1:删除）
     */
    @ApiModelProperty(value = "物理删除状态 （0:正常;1:删除）")
    private Integer delStatus;

    public static List<RoleDetailDto> createRoleDetailDtoList(List<Role> roleList, List<RoleResourceDto> roleResourceDtoList) {
        Map<Integer, RoleResource> roleResourceDtoMap = new HashMap<>();
        Map<Integer, RoleDetailDto> roleDetailMap = new HashMap<>();
        roleList.forEach(r->{
            roleDetailMap.put(r.getId(), BeanUtil.copyProperties(r, RoleDetailDto.class));
        });
        List<RoleDetailDto> roleDetailDtoList = new ArrayList<>();
        roleResourceDtoList.forEach(rc->{
            roleDetailMap.get(rc.getRoleId()).setRoleResource(rc);
            roleDetailDtoList.add(roleDetailMap.get(rc.getRoleId()));
        });
        return roleDetailDtoList;
    }
}
