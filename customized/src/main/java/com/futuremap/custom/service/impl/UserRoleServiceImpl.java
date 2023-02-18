package com.futuremap.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.custom.entity.UserRole;
import com.futuremap.custom.mapper.UserRoleMapper;
import com.futuremap.custom.service.AuthUserRoleService;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements AuthUserRoleService {

	/**
	 * @param userIds
	 */
	public List<UserRole> selectByUserIdIn(List<String> userIds) {
		QueryWrapper<UserRole> urQuery = new QueryWrapper<UserRole>();
		urQuery.in("user_id", userIds);
		return getBaseMapper().selectList(urQuery);		
	}

	/**
	 * @param userId
	 */
	public void removeByUserId(String userId) {
		this.baseMapper.deleteByUserId(userId);
	}

	/**
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId) {
		this.baseMapper.deleteByRoleId(roleId);
	}

}
