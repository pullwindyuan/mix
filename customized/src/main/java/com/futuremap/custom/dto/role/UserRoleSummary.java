package com.futuremap.custom.dto.role;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色用户列表")
public class UserRoleSummary {

	
	 private String id;
	 
	 private String roleId;
	 
	 private String userId;
	 
	 private String  roleName;
	 
}
