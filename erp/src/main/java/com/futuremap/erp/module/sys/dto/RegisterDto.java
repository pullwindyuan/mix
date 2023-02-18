package com.futuremap.erp.module.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户创建 绑定请求")
public class RegisterDto {

	@ApiModelProperty(name = "用户密码", required = true)
	@Length(min = 8, max = 16, message = "密码至少8-16个字符(字母或数字)，至少1个大写字母，1个小写字母和1个数字")

	private String password;

	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("微信扫码后注册时需要传, 直接注册不需要传")
	private String unionId;

	@ApiModelProperty("微信扫码时时需要传, 直接注册不需要传")
	private String webOpenId;

	@ApiModelProperty("微信昵称微信扫码时时需要传, 直接注册不需要传")
	private String wxNickName;
	
	@ApiModelProperty("昵称")
	private String name;
}
