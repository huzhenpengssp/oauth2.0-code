package com.authorization.dao;

import com.authorization.domain.ClientDetail;

public interface ClientDetailMapper {
    int deleteByPrimaryKey(String clientId);

    int insert(ClientDetail record);

    int insertSelective(ClientDetail record);

    ClientDetail selectByPrimaryKey(String clientId);

    int updateByPrimaryKeySelective(ClientDetail record);

    int updateByPrimaryKey(ClientDetail record);
}