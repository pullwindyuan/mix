package com.futuremap.erp.common.security.impl;

import com.futuremap.erp.common.security.entity.CustomUserDetails;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserServiceImpl userService;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDetails loadUserByUsername(String phone) {
		User user = userService.selectByPhone(phone);
		user.setName(phone);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", phone));
		} else {
			CustomUserDetails userDetails =  new CustomUserDetails(
					user,
					userService.getRoleList(user.getId()));
			return userDetails;
		}
	}


}
