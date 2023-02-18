package com.futuremap.custom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futuremap.custom.dto.UserDetail;
import com.futuremap.custom.dto.function.FunctionSummary;
import com.futuremap.custom.dto.user.UserCreate;
import com.futuremap.custom.dto.user.UserUpdate;
import com.futuremap.custom.entity.Role;
import com.futuremap.custom.entity.User;
import com.futuremap.custom.entity.UserRole;
import com.futuremap.custom.exception.BadRequestException;
import com.futuremap.custom.mapper.FunctionMapper;
import com.futuremap.custom.mapper.UserMapper;
import com.futuremap.custom.mapper.UserRoleMapper;
import com.futuremap.custom.service.impl.FunctionServiceImpl;
import com.futuremap.custom.service.impl.RoleFunctionServiceImpl;
import com.futuremap.custom.service.impl.RoleServiceImpl;
import com.futuremap.custom.util.Const;

@Service
//使用master 库
@DS("master")
public class UserService  {

	protected final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserMapper userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	RoleServiceImpl roleService;
	
	@Autowired
	UserRoleMapper userRoleRepository;
	
	@Autowired
	FunctionServiceImpl functionService;
	
	@Autowired
	RoleFunctionServiceImpl  roleFunctionService;


	public User findByName(String username) {
		return userRepository.selectByName(username);
	}

	public User findByEmail1(String email) {
		return userRepository.selectByEmail(email);
	}

	public User findById(String userId) {
		return userRepository.selectById(userId);
	}

	/**
	 * @return
	 */
	public UserDetail getUser() {
		String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
		User user = findById(userId);
		UserDetail userDetail =  modelMapper.map(user, UserDetail.class);
		// 获取用户角色
		List<UserRole> urs = userRoleRepository.selectByUserId(userId);
		
		List<String> ids = urs.stream().map(UserRole::getRoleId).collect(Collectors.toList());
		
		List<Role> roles = roleService.findByIdIn(ids);
		
		List<FunctionSummary> functions = functionService.selectByMethodTypeAndMethodNameIsNotNull("1").stream()
				.map(e -> modelMapper.map(e, FunctionSummary.class)).peek(e -> e.setAuth(1))
				.collect(Collectors.toList());
		return userDetail;
	}



	public void createUser(UserCreate userCreate) {
        User user = modelMapper.map(userCreate, User.class);
        
        if(userRepository.selectByPhone(userCreate.getPhone()).size()>0) {
        	throw new BadRequestException("此手机号己注册");
        }
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		// 添加用户信息
		user.setAvatar(Const.User.AVATAR);
		userRepository.save(user);
		String userId = user.getId();
		// 添加角色信息 暂时没限制一个目标管理人
		List<String> roleIds = userCreate.getRoleIds();
		if(roleIds == null) {
			throw new BadRequestException("至少添加一个角色");
		}
		List<UserRole> urs = roleIds.stream().map(e->{
			UserRole userRole = new UserRole();
	        userRole.setUserId(userId);
	   	   	userRole.setRoleId(e);
	   	   	return userRole;
		}).collect(Collectors.toList());
		//userRoleRepository.saveBatch(urs);
	}

	/**
	 * @param userUpdate
	 */
	public void udpateMyself(UserUpdate userUpdate) {
		String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
		User user = findById(userId);
		if (userRepository.selectByPhoneAndIdNot(user.getPhone(), userId).size() > 1) {
			throw new BadRequestException("此手机号己注册");
		}
		if (!StringUtils.isEmpty(userUpdate.getPassword())) {
			user.setPassword(new BCryptPasswordEncoder().encode(userUpdate.getPassword()));
		}

		// 添加用户信息
		modelMapper.map(userUpdate, user);
		userRepository.save(user);
	}

	
}
