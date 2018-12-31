package com.qskx.transaction.dao;

import com.qskx.transaction.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order order);

    int insertSelective(Order order);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order order);

    int updateByPrimaryKey(Order order);
}