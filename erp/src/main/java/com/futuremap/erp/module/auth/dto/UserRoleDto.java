package com.futuremap.erp.module.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.RoleResource;
import com.futuremap.erp.module.auth.entity.UserRole;
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
 * 授权用户角色
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserRoleDto对象", description="授权用户角色")
public class UserRoleDto implements Serializable {

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
     * 角色详情信息
     */
    @ApiModelProperty(value = "角色详情信息")
    private RoleDetailDto role;

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

    public UserRoleDto() {
        super();
    }

    public UserRoleDto(UserRole userRole, RoleDetailDto roleDto) {
        super();
        this.id = userRole.getId();
        this.companyId = userRole.getCompanyId();
        this.roleId = userRole.getRoleId();
        this.userId = userRole.getUserId();
        this.delStatus = userRole.getDelStatus();
        this.role = roleDto;
    }

    public static List<UserRoleDto> createUserRoleDtoList(List<UserRole> userRoleList, List<RoleDetailDto> roleDtoList) {
        Map<Integer, RoleDetailDto> roleDtoMap = new HashMap<>();
        roleDtoList.forEach(rd->{
            roleDtoMap.put(rd.getId(), rd);
        });
        List<UserRoleDto> userRoleDtoList = new ArrayList<>();
        userRoleList.forEach(ur->{
            UserRoleDto userRoleDto = new UserRoleDto(ur,roleDtoMap.get(ur.getRoleId()));
            userRoleDtoList.add(userRoleDto);
        });
        return userRoleDtoList;
    }
}
