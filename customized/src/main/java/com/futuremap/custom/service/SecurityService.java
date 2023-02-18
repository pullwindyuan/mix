package com.futuremap.custom.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futuremap.custom.dto.LoginRequest;
import com.futuremap.custom.entity.User;
import com.futuremap.custom.exception.UnAuthorizedException;
import com.futuremap.base.security.UserDetailsServiceImpl;
import com.futuremap.custom.token.TokenService;

@Service
//@DS("master")
public class SecurityService {
	protected final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> login(LoginRequest loginRequest) {
		// 检查登录次数
		// authLogService.checkAuthTimes(request, loginRequest.getUsername());
		User user = null;
		try {
			// 跟据登录参数获取用户
			user = userDetailsService.getUserByRequest(loginRequest);
			// username和password被获得后封装到一个UsernamePasswordAuthenticationToken（Authentication接口的实例）的实例中
			// 这个token被传递给AuthenticationManager进行验证
			// 验证时会调用loadUserByUsername
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getPhone(), loginRequest.getPassword()));
			// 围绕该用户建立安全上下文（security context）
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			logger.info("账号或密码错误!", e);
			throw new UnAuthorizedException("账号或密码错误!");
		}
		//authLogService.saveSucc(request, user, loginRequest.getUsername());
		Map<String, String> tokenMap = new HashMap<String, String>(2);
		tokenMap.put("token", tokenService.generateToken(user));
		return tokenMap;

	}
	
	
	
}
