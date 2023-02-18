package com.futuremap.custom.dto.role;

import java.util.List;

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
@ApiModel("新增用户角色")
public class UserRoleCreate {
	
	
	 private String roleId;
	 
	 @ApiModelProperty("用户")
	 private String userId;
	 
	 @ApiModelProperty("用户角色(ADMIN, EMPLOYEE, MANAGER, NOTICE)")
	 private String roleName;
	 
	 @ApiModelProperty("多个用户")
	 private List<String> userIds;

}
