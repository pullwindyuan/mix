package com.futuremap.erp.module.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录请求")
public class LoginRequest {

    @ApiModelProperty("手机号")
    private String phone;
    
    @ApiModelProperty("密码")
    private String password;

}
