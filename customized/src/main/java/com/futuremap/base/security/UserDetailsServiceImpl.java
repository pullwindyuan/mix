package com.futuremap.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.futuremap.custom.dto.LoginRequest;
import com.futuremap.custom.entity.User;
import com.futuremap.custom.service.impl.UserServiceImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    
    @Autowired
    private UserServiceImpl userService;

    
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDetails loadUserByUsername(String phone) {
		User user = userService.selectByPhone(phone);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", phone));
		} else {
			UserDetails userDetails =  new org.springframework.security.core.userdetails.User(
					phone,
					user.getPassword(),
					AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
			return userDetails;
		}
	}
    
    
	public User getUserByRequest(LoginRequest loginRequest) throws UsernameNotFoundException {

		User user = userService.selectByPhone(loginRequest.getPhone());
		if (user == null) {
			throw new UsernameNotFoundException(
					String.format("No user found with phone '%s'.", loginRequest.getPhone()));
		} else {
			new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
					AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
		}
		return user;
	}
    
  
    
    
}
