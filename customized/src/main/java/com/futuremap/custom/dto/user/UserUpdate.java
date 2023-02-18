package com.futuremap.custom.dto.user;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户修改")
public class UserUpdate {
	
	@ApiModelProperty("用户ID")
	@NotBlank
	private String id;

	@ApiModelProperty("旧密码")
	private String oldPassword;

	@ApiModelProperty("新密码")
	private String password;

	@ApiModelProperty("职位ID")
	private String position;

	@ApiModelProperty("头像")
	private String avatar;
	
	@ApiModelProperty("手机")
	private String phone;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("角色ID列表")
	private List<String> roleIds;

}
