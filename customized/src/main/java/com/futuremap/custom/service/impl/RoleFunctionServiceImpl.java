package com.futuremap.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.custom.entity.AuthRoleFunction;
import com.futuremap.custom.entity.RoleFunction;
import com.futuremap.custom.entity.UserRole;
import com.futuremap.custom.mapper.AuthUserMapper;
import com.futuremap.custom.mapper.FunctionMapper;
import com.futuremap.custom.mapper.RoleFunctionMapper;
import com.futuremap.custom.service.RoleFunctionService;

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
public class RoleFunctionServiceImpl extends ServiceImpl<RoleFunctionMapper, RoleFunction> implements RoleFunctionService {

	public RoleFunctionMapper getMapper() {
        return this.baseMapper;
    }
	
	
	/**
	 * @param userId
	 * @return
	 */
	public List<UserRole> selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return getMapper().selectByUserId(userId);
	}


	/**
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId) {
		 getMapper().deleteByRoleId(roleId);
	}


	/**
	 * @param roleId
	 * @return
	 */
	public List<RoleFunction> selectByRoleId(String roleId) {
		return getMapper().selectByRoleId(roleId);
	}


	/**
	 * @param ids
	 * @return
	 */
	public List<RoleFunction> selectByRoleIdIn(List<String> ids) {
		QueryWrapper<RoleFunction> qw = new QueryWrapper<RoleFunction>();
		qw.in("role_id", ids);
		return this.baseMapper.selectList(qw);
	}

}
