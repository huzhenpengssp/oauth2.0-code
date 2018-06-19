package com.authorization.dao;

import com.authorization.domain.RefreshToken;

public interface RefreshTokenMapper {
    int insert(RefreshToken record);

    int insertSelective(RefreshToken record);
    
    RefreshToken selectByTokenId(String tokenId);

    int deleteByTokenId(String tokenId);
}