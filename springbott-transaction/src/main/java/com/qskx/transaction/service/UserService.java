package com.qskx.transaction.service;

import com.qskx.transaction.entity.Order;
import com.qskx.transaction.entity.User;

/**
 * @author 111111
 * @date 2018-12-31 20:20
 */
public interface UserService {

    void insertUserAndOrderInfo(User user, Order order);
}
