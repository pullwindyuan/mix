package com.futuremap.custom.dto;

import java.io.Serializable;
import java.util.List;

import com.futuremap.custom.dto.role.UserRoleSummary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户详情")
public class UserDetail implements Serializable  {
	private static final long serialVersionUID = 3621271437883980835L;

	@ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户头像")
    private String avatar;
    

    @ApiModelProperty("手机号码")
    private String phone;
    
    @ApiModelProperty("职位")
    private String position;
    
	@ApiModelProperty("小程序openId")
    private String miniOpenId;
	
	@ApiModelProperty("网页openId")
    private String webOpenId;

	@ApiModelProperty("unionId")
    private String unionId;
    
    @ApiModelProperty("微信昵称")
    private String nickName;
    
    @ApiModelProperty("用户状态(1正常2禁用)")
    private Integer userStatus;
    
    @ApiModelProperty("用户角色")
    private List<UserRoleSummary> userRoles;
    
}
