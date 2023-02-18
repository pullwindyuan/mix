package com.futuremap.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.RoleFunction;
import com.futuremap.custom.entity.UserRole;

@Mapper
public interface RoleFunctionMapper extends BaseMapper<RoleFunction>{

	/**
	 * @param userId
	 * @return
	 */
	@Select("select * from auth_user_role where user_id = #{userId}")
	List<UserRole> selectByUserId(String userId);

	
	
	/**
	 * @param userId
	 * @return
	 */
	@Select("select * from auth_role_function where role_id = #{roleId}")
	List<RoleFunction> selectByRoleId(String roleId);
	
	/**
	 * @param roleId
	 * @return
	 */
    @Delete("delete from auth_role_function where role_id = #{roleId}")
	void deleteByRoleId(String roleId);
	
}
