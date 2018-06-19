package com.authorization.dao;

import com.authorization.domain.User;

public interface UserMapper {
    

	User selectByUserName(String username);
}