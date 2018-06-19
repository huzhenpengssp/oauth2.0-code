package com.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authorization.dao.UserMapper;
import com.authorization.domain.User;

@Component
public class UserinfoService implements UserDetailsService{
	
	@Autowired
	private UserMapper userMapper;
	
	public List<User> selectUserInfo() {
		
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userMapper.selectByUserName(username);
		return user;
	}
}
