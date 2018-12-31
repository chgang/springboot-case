package com.qskx.transaction.dao;

import com.qskx.transaction.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}