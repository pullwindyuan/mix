package com.futuremap.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.UserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * @param userIds
	 */
	List<UserRole> selectByUserIdIn(List<String> userIds);

	/**
	 * @param userId
	 */
	@Delete("delete from auth_user_role where user_id = #{userId}")
	void deleteByUserId(String userId);
	
	/**
	 * @param userId
	 */
	@Delete("delete from auth_user_role where role_id = #{roleId}")
	void deleteByRoleId(String roleId);
	
	/**
	 * @param userId
	 * @return
	 */
	@Select("select * from auth_user_role where user_id = #{userId}#")
	List<UserRole> selectByUserId(String userId);


}
