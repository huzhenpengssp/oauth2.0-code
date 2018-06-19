package com.authorization.dao;

import com.authorization.domain.Code;

public interface CodeMapper {
    int insert(Code record);

    int insertSelective(Code record);
}