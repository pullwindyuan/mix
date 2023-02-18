/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.module.sys.service.impl;

import com.futuremap.erp.common.exception.UnAuthorizedException;
import com.futuremap.erp.common.security.entity.CustomUserDetails;
import com.futuremap.erp.common.security.impl.MemoryTokenServiceImpl;
import com.futuremap.erp.common.security.impl.UserDetailsServiceImpl;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import com.futuremap.erp.module.sys.dto.RegisterDto;
import com.futuremap.erp.module.sys.dto.TokenDto;
import com.futuremap.erp.module.sys.entity.LoginRequest;
import com.futuremap.erp.module.sys.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @title LoginService
 * @description TODO
 * @date 2021/6/19 17:10
 */
@Service
@Slf4j
public class LoginService implements ILoginService {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MemoryTokenServiceImpl memoryTokenServiceImpl;

    @Autowired
    private ModelMapper modelMapper;
    @Value("${security.token.head}")
    private String tokenHead;

    @Value("${security.token.expiration}")
    private Long expiration;

    @Override
    public TokenDto login(LoginRequest loginRequest) {
        TokenDto tokenDto = new TokenDto();
        try {
            // 跟据登录参数获取用户
//            user = userService.selectByPhone(loginRequest.getPhone());
            // username和password被获得后封装到一个UsernamePasswordAuthenticationToken（Authentication接口的实例）的实例中
            // 这个token被传递给AuthenticationManager进行验证
            // 验证时会调用loadUserByUsername
//            if (!passwordEncoder.matches(loginRequest.getPassword(), customUserDetails.getPassword())) {
//                throw new BadCredentialsException("密码不正确");
//            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword()));
            // 围绕该用户建立安全上下文（security context）
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getPhone());
//            userDetails.getUser().setName(loginRequest.getPhone());
            tokenDto.setToken(memoryTokenServiceImpl.generateToken(userDetails));
            tokenDto.setHead(tokenHead);
            tokenDto.setExpiration(expiration);
            //获取当前用户及角色信息
            UserDto currentUser = userService.getCurrentUser();
            tokenDto.setUserDto(currentUser);
        }catch (BadCredentialsException e) {
            log.info("用户名或密码不正确", e);
            throw new UnAuthorizedException("用户名或密码不正确");
        } catch (Exception e) {
            log.info("登录异常", e);
            throw new UnAuthorizedException("登录异常");
        }
        return tokenDto;
    }

    @Override
    public void register(RegisterDto registerDto) {
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
    }


    @Override
    public TokenDto refreshToken(String oldToken) {
        String refreshToken = memoryTokenServiceImpl.refreshToken(oldToken);
        TokenDto dto = new TokenDto();
        if(refreshToken!=null){
            dto.setToken(refreshToken);
            dto.setHead(tokenHead);
            dto.setExpiration(expiration);
        }
        return dto;
    }
}
