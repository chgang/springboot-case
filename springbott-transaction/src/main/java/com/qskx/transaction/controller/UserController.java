package com.qskx.transaction.controller;

import com.qskx.transaction.entity.Order;
import com.qskx.transaction.entity.User;
import com.qskx.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 111111
 * @date 2018-12-31 20:33
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add/user/order/info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addUserAndOrderInfo(User user, Order order) {

        userService.insertUserAndOrderInfo(user, order);
        return "success";
    }
}
