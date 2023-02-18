package com.futuremap.custom.dto.user;

import javax.validation.constraints.NotBlank;
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
@ApiModel("忘记密码")
public class ForgetPwdCreate {
	
	private String name;

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
