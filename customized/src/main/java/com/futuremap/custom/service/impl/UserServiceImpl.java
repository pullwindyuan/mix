package com.futuremap.custom.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.custom.dto.UserDetail;
import com.futuremap.custom.dto.function.FunctionSummary;
import com.futuremap.custom.dto.role.UserRoleSummary;
import com.futuremap.custom.dto.user.UserCreate;
import com.futuremap.custom.dto.user.UserUpdate;
import com.futuremap.custom.entity.AuthUser;
import com.futuremap.custom.entity.Role;
import com.futuremap.custom.entity.User;
import com.futuremap.custom.entity.UserRole;
import com.futuremap.custom.exception.BadRequestException;
import com.futuremap.custom.mapper.AuthUserMapper;
import com.futuremap.custom.service.IUserService;
import com.futuremap.custom.service.UserService;
import com.futuremap.custom.util.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Service
@DS("master")
public class UserServiceImpl extends ServiceImpl<AuthUserMapper, User> implements IUserService {
	
	protected final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	RoleFunctionServiceImpl roleFunctionService;
	
	@Autowired
	RoleServiceImpl roleService;
	
	@Autowired
	UserRoleServiceImpl userRoleService;
	
	@Autowired
	FunctionServiceImpl functionService;

	public AuthUserMapper getMapper() {
        return this.baseMapper;
    }
	
	@Override
	public User selectByEmail(String username) {
		return getMapper().selectByEmail(username);
	}

	@Override
	public User selectByPhone(String phone) {
		return getMapper().selectByPhone(phone);
	}

	/**
	 * @param userId
	 * @return
	 */
	public User findById(String userId) {
		return getMapper().selectById(userId);
	}
	
	/**
	 * @return
	 */
	public UserDetail getUser() {
		String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
		User user = findById(userId);
		UserDetail userDetail =  modelMapper.map(user, UserDetail.class);
		// 获取用户角色
		List<UserRole> urs = roleFunctionService.selectByUserId(userId);
		
		List<String> ids = urs.stream().map(UserRole::getRoleId).collect(Collectors.toList());
		List<UserRoleSummary> roles = roleService.findByIdIn(ids).stream().map(e->modelMapper.map(e, UserRoleSummary.class)).collect(Collectors.toList());
		userDetail.setUserRoles(roles);
		List<FunctionSummary> functions = functionService.selectByMethodTypeAndMethodNameIsNotNull("1").stream()
				.map(e -> modelMapper.map(e, FunctionSummary.class)).peek(e -> e.setAuth(1))
				.collect(Collectors.toList());
		return userDetail;
	}



	public void createUser(UserCreate userCreate) {
		User user = modelMapper.map(userCreate, User.class);

		if (selectByPhone(userCreate.getPhone()) != null) {
			throw new BadRequestException("此手机号己注册" + "[" + userCreate.getPhone() + "]");
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		// 添加用户信息
		user.setAvatar(Const.User.AVATAR);
		getMapper().insert(user);
		String userId = user.getId();
		// 添加角色信息 暂时没限制一个目标管理人
		List<String> roleIds = userCreate.getRoleIds();
		if (roleIds == null) {
			throw new BadRequestException("至少添加一个角色");
		}
		List<UserRole> urs = roleIds.stream().map(e -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(e);
			return userRole;
		}).collect(Collectors.toList());
		// 有时间 改成批量插入
		userRoleService.saveBatch(urs);
	}

	
	public void createUser(List<UserCreate> userCreates) {
		List<User> users = userCreates.stream().map(userCreate -> (modelMapper.map(userCreate, User.class)))
				.peek(e -> e.setPassword(new BCryptPasswordEncoder().encode(e.getPassword())))
				.collect(Collectors.toList());

		userCreates.forEach(userCreate -> {
			if (selectByPhone(userCreate.getPhone()) != null) {
				throw new BadRequestException("此手机号己注册" + "[" + userCreate.getPhone() + "]");
			}
		});

		this.saveBatch(users);
		List<UserRole> saveUr = new ArrayList<>();
		for (int i = 0; i < userCreates.size(); i++) {
			UserCreate userCreate = userCreates.get(i);
			User user = users.get(i);
			// 添加角色信息 暂时没限制一个目标管理人
			List<String> roleIds = userCreate.getRoleIds();
			if (roleIds == null) {
				throw new BadRequestException("至少添加一个角色");
			}

			List<UserRole> urs = roleIds.stream().map(e -> {
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(e);
				return userRole;
			}).collect(Collectors.toList());
			saveUr.addAll(urs);
		}
		userRoleService.saveBatch(saveUr);
	}
	
	/**
	 * 修改自己信息
	 * @param userUpdate
	 */
	public void udpateMyself(UserUpdate userUpdate) {
		String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
		User user = findById(userId);
		if (selectByPhoneAndIdNot(user.getPhone(), userId).size() > 1) {
			throw new BadRequestException("此手机号己注册");
		}
		if (!StringUtils.isEmpty(userUpdate.getPassword())) {
			user.setPassword(new BCryptPasswordEncoder().encode(userUpdate.getPassword()));
		}

		// 添加用户信息
		modelMapper.map(userUpdate, user);
		getMapper().insert(user);
	}

	/**
	 * @param phone
	 * @param userId
	 * @return
	 */
	private List<UserRole> selectByPhoneAndIdNot(String phone, String userId) {
		return getMapper().selectByPhoneAndIdNot(phone, userId);
	}
	
	/**
	 * @param phone
	 * @param userId
	 * @return
	 */
	private int countByPhoneAndIdNot(String phone, String userId) {
		return getMapper().countByPhoneAndIdNot(phone, userId);
	}
	
	
	public Map<String, User> findUserMapByIdIn(List<String> ids) {
		
		return findByIdIn(ids).stream().collect(Collectors.toMap(User::getId, e->e));
	}
	
    public List<User> findByIdIn(List<String> ids) {
		if(CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		QueryWrapper<User> qw = new QueryWrapper<User>();
		 qw.in("id", ids);
		return this.baseMapper.selectList(qw);
	}
	
	

	/**
	 * 获取用户列表
	 */
	public List<UserDetail> queryUser() {
		List<UserDetail> users = this.list(null).stream().map(e -> modelMapper.map(e, UserDetail.class))
				.collect(Collectors.toList());
		List<String> userIds = users.stream().map(e -> e.getId()).collect(Collectors.toList());
		List<UserRoleSummary> urs = userRoleService.selectByUserIdIn(userIds).stream()
				.map(e -> modelMapper.map(e, UserRoleSummary.class)).collect(Collectors.toList());
		Map<String, Role> rMap = roleService
				.selectMapByIdIn(urs.stream().map(e -> e.getRoleId()).collect(Collectors.toList()));
		urs.forEach(e -> e.setRoleName(rMap.getOrDefault(e.getRoleId(), new Role()).getRoleName()));
		Map<String, List<UserRoleSummary>> urMap = urs.stream().collect(Collectors.groupingBy(e -> e.getUserId()));
		users.forEach(u -> u.setUserRoles(urMap.getOrDefault(u.getId(), new ArrayList<>())));
		return users;
	}

	/**
	 * @param userUpdate
	 */
	public void updateUser(UserUpdate userUpdate) {
		String userId = userUpdate.getId();
		User user = findById(userId);
		if (countByPhoneAndIdNot(user.getPhone(), userId) > 1) {
			throw new BadRequestException("此手机号己注册");
		}
		if (!StringUtils.isEmpty(userUpdate.getPassword())) {
			user.setPassword(new BCryptPasswordEncoder().encode(userUpdate.getPassword()));
		}
		// 添加用户信息
		modelMapper.map(userUpdate, user);
		this.saveOrUpdate(user);

		// 添加角色信息 暂时没限制一个目标管理人
		List<String> roleIds = userUpdate.getRoleIds();
		if (CollectionUtils.isEmpty(roleIds)) {
			throw new BadRequestException("至少添加一个角色");
		}
		List<UserRole> urs = roleIds.stream().map(e -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(e);
			return userRole;
		}).collect(Collectors.toList());
		userRoleService.removeByUserId(userId);
		userRoleService.saveBatch(urs);
	}
	

}
