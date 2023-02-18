package com.futuremap.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.AuthUser;
import com.futuremap.custom.entity.User;
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
public interface AuthUserMapper extends BaseMapper<User> {

	/**
	 * @param username
	 * @return
	 */
	User selectByEmail(String username);

	/**
	 * @param phone
	 * @return
	 */
	@Select("select * from auth_user where phone = #{phone}")
	User selectByPhone(String phone);

	/**
	 * @param phone
	 * @param userId
	 * @return
	 */
	@Select("select * from auth_user where phone = #{phone} and id != #{userId}")
	List<UserRole> selectByPhoneAndIdNot(String phone, String userId);
	
	/**
	 * @param phone
	 * @param userId
	 * @return
	 */
	@Select("select count(*) from auth_user where phone = #{phone} and id != #{userId}")
	int countByPhoneAndIdNot(String phone, String userId);

}
