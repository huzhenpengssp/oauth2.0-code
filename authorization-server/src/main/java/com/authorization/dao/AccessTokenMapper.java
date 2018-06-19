package com.authorization.dao;

import com.authorization.domain.AccessToken;
import com.authorization.domain.AccessTokenWithBLOBs;

import java.util.HashMap;
import java.util.List;

public interface AccessTokenMapper {
	
	int deleteByTokenId(String tokenId);
	
    int deleteByPrimaryKey(String authenticationId);

    int insert(AccessTokenWithBLOBs record);

    int insertSelective(AccessTokenWithBLOBs record);

    AccessTokenWithBLOBs selectByPrimaryKey(String authenticationId);

    int updateByPrimaryKeySelective(AccessTokenWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AccessTokenWithBLOBs record);

    int updateByPrimaryKey(AccessToken record);
    
    AccessTokenWithBLOBs selectByTokenId(String tokenId);

    int deleteByRefreshTokenId(String refreshTokenId);

    List<AccessTokenWithBLOBs> selectByClientIdAndUserName(HashMap<String,String> map);

    List<AccessTokenWithBLOBs> selectByClientId(String clientId);
}