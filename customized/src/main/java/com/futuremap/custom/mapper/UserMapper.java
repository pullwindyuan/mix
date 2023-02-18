package com.futuremap.custom.mapper;


import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.custom.entity.User;

@Mapper
public interface UserMapper  extends IService<User> {
	
	User findById(String id);
	User selectById(String id);
	/**
	 * @param email
	 * @return
	 */
	User selectByEmail(String email);
	/**
	 * @param username
	 * @return
	 */
	User selectByName(String username);
	
	/**
	 * @param phone
	 * @return
	 */
	List<User> selectByPhone(String phone);
	/**
	 * @param name
	 * @param userId
	 * @return
	 */
	List<User> selectByNameAndIdNot(String name, String userId);
	/**
	 * @param phone
	 * @param userId
	 * @return
	 */
	List<User> selectByPhoneAndIdNot(String phone, String userId);
	
}
