package com.futuremap.custom.dto.role;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleCreate {
	
	@ApiModelProperty("角色名")
	@NotEmpty
	@Length(min = 1, max = 45, message = "名称必须在1到45位之间")
	private String roleName;
	
	@ApiModelProperty("详情")
	//@NotEmpty
	//@Length(min = 1, max = 200, message = "用户名必须在1到200位之间")
	private String detail;
}
