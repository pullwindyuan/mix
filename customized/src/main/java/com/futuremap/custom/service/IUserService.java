package com.futuremap.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.custom.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
public interface IUserService extends IService<User> {

	/**
	 * @param username
	 * @return
	 */
	User selectByEmail(String username);
	
	User selectByPhone(String phone);

}
