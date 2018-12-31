package com.qskx.transaction.service.impl;

import com.qskx.transaction.dao.OrderMapper;
import com.qskx.transaction.dao.UserMapper;
import com.qskx.transaction.entity.Order;
import com.qskx.transaction.entity.User;
import com.qskx.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 111111
 * @date 2018-12-31 20:23
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional("transactionManager")
    public void insertUserAndOrderInfo(User user, Order order) {

        userMapper.insert(user);

//        orderMapper.insert(order);
//        int i = 1/0;
    }
}
