package com.futuremap.custom.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@ApiModel("用户验证码")
public class VerifycodeCreate {
	
	
	@ApiModelProperty("用户手机")
	@NotBlank
	@Length(min = 11, max = 11, message = "手机号码为11位数据")
	private String phone;
	
	@ApiModelProperty("用户名")
	private String name;
	
	@ApiModelProperty("类型1注册2忘记密码")
	@NotNull
	private Integer type;
}
