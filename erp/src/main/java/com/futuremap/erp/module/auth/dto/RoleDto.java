package com.futuremap.erp.module.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value="RoleDto对象", description="授权角色Dto")
public class RoleDto implements Serializable {

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
     * 角色菜单
     */
    @ApiModelProperty(value = "角色菜单")
    private RoleResource roleResource;

    /**
     * 角色数据列权限信息
     */
    @ApiModelProperty(value = "角色数据列权限信息")
    private RoleColumnDto roleColumn;

    /**
     * 物理删除状态 （0:正常;1:删除）
     */
    @ApiModelProperty(value = "物理删除状态 （0:正常;1:删除）")
    private Integer delStatus;

    public RoleDto() {
        super();
    }

    public RoleDto(RoleResource roleResource, RoleColumnDto roleColumnDto) {
        super();
        this.roleResource = roleResource;
        this.roleColumn = roleColumnDto;
    }

    public RoleDto(Role role, RoleResource roleResource, RoleColumnDto roleColumnDto) {
        super();
        this.id = role.getId();
        this.roleName = role.getRoleName();
        this.roleDesc = role.getRoleDesc();
        this.detail = role.getDetail();
        this.delStatus = role.getDelStatus();
        this.roleResource = roleResource;
        this.roleColumn = roleColumnDto;
    }

    public static List<RoleDto> createRoleDtoList(List<Role> roleList, List<RoleResource> roleResourceList, List<RoleColumnDto> roleColumnDtoList) {
        Map<Integer, RoleResource> roleResourceMap = new HashMap<>();
        Map<Integer, RoleColumnDto> roleColumnDtoMap = new HashMap<>();
        roleResourceList.forEach(rr->{
            roleResourceMap.put(rr.getRoleId(), rr);
        });
        roleColumnDtoList.forEach(rc->{
            roleColumnDtoMap.put(rc.getRoleId(), rc);
        });
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleList.forEach(r->{
            RoleDto roleDto = new RoleDto(r,roleResourceMap.get(r.getId()), roleColumnDtoMap.get(r.getId()));
            roleDtoList.add(roleDto);
        });
        return roleDtoList;
    }
}
