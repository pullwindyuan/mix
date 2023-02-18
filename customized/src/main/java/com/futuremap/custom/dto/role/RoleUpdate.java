package com.futuremap.custom.dto.role;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel("更新用户角色")
public class RoleUpdate {
	
	
	private String id;
	
	@ApiModelProperty("角色名")
	@NotEmpty
	@Length(min = 1, max = 45, message = "用户名必须在1到45位之间")
	private String roleName;
	
}
