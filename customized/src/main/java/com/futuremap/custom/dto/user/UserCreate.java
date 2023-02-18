package com.futuremap.custom.dto.user;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import com.futuremap.custom.util.Const;

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
public class UserCreate {
    
    @ApiModelProperty("用户角色ID数组")
    @NotEmpty
    private List<String> roleIds;
    
	@ApiModelProperty("用户名")
	@NotEmpty
	@Length(min = 1, max = 22, message = "岗位必须在1到22位之间")
	private String position;
	
	@ApiModelProperty("用户姓名")
	@Length(min = 1, max = 22, message = "用户名必须在1到22位之间")
	private String name;
	
	
	@ApiModelProperty("用户密码")
	@NotBlank
	@Length(min = 6, max = 14, message = "用户密码必须在6到14位之间")
	private String password;
	
	@ApiModelProperty("用户手机")
	@NotBlank
	@Length(min = 11, max = 11, message = "手机号码为11位数据")
	private String phone;
	
	
	private final String avatar = Const.User.AVATAR;
}
