package com.futuremap.custom.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
@ApiModel("用户创建请求")
public class RegisterCreate {

	@ApiModelProperty("公司名称")
	@NotEmpty
	@Length(min = 1, max = 22, message = "用户名必须在1到22位之间")
	private String name;
	
	@Length(min = 1, max = 22, message = "用户名必须在1到22位之间")
	private String fullName;
	
	@ApiModelProperty("公司名称")
	@NotEmpty
	@Length(min = 1, max = 22, message = "用户名必须在1到22位之间")
	private String companyName;
	
	@ApiModelProperty("公司信用号")
	//@NotEmpty
	@Length(min = 0, max = 22, message = "公司信用号必须在0到22位之间")
	private String companyCode;
	
	@ApiModelProperty("用户密码")
	@NotBlank
	@Length(min = 6, max = 14, message = "用户密码必须在6到14位之间")
	private String password;
	
	@ApiModelProperty("用户手机")
	@NotBlank
	@Length(min = 11, max = 11, message = "手机号码为11位数据")
	private String phone;
	
	@ApiModelProperty("验证码")
	@NotBlank
	@Length(min = 6, max = 6, message = "验证码为6位")
	private String verCode;

}
