package com.futuremap.custom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.LoginRequest;
import com.futuremap.custom.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.validation.Valid;

@RestController
@Api(tags = "认证接口", description = "登录等")
public class SecurityController {
	protected final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    
    @Autowired
    private SecurityService securityService;
    
    @PostMapping("/auth/login")
    @ApiOperation("登录")
    @CustomResponse
    public Map<String, String> login(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
    	return securityService.login(loginRequest);
    }
    
    
    @PostMapping("/auth/test")
    @ApiOperation("测试")
    public String test() {
    	return "test";
    }
    
}
