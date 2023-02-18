package com.futuremap.erp.module.sys.controller;


import com.futuremap.erp.module.sys.dto.RegisterDto;
import com.futuremap.erp.module.sys.dto.TokenDto;
import com.futuremap.erp.module.sys.entity.LoginRequest;
import com.futuremap.erp.module.sys.service.impl.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 登录
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@RestController
@RequestMapping("/sys")
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public TokenDto login(@RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }



    @PostMapping("/register")
    @ApiOperation("用户注册")
    public void login(@Valid @RequestBody RegisterDto registerDto)  {
        loginService.register(registerDto);
    }


    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken")
    @ResponseBody
    public TokenDto refreshToken(@RequestBody TokenDto tokenDto) {
        String token = tokenDto.getToken();
        TokenDto dto = loginService.refreshToken(token);
        return dto;
    }

}
