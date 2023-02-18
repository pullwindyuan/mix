package com.futuremap.erp.module.sys.service;

import com.futuremap.erp.module.sys.dto.RegisterDto;
import com.futuremap.erp.module.sys.dto.TokenDto;
import com.futuremap.erp.module.sys.entity.LoginRequest;

/**
 * @author Administrator
 * @title ILoginService
 * @description TODO
 * @date 2021/6/19 17:10
 */
public interface ILoginService {

    public TokenDto login(LoginRequest loginRequest);

    void register(RegisterDto registerDto);

    TokenDto refreshToken(String oldToken);
}
