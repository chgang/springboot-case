package com.qskx.jsonrpc.server.impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.qskx.jsonrpc.impl.ExecuteOrderImpl;
import com.qskx.jsonrpc.server.ServerTestAPI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.server.impl
 * @ClassName: ServerTestAPIImpl
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 15:27
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Service
@AutoJsonRpcServiceImpl
public class ServerTestAPIImpl implements ServerTestAPI, BeanPostProcessor {

    @Override
    public int multiplier(int a, int b) {
//        int i = 1/0;
        return a * b;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("executeOrderImpl")) {
            ExecuteOrderImpl executeOrder = (ExecuteOrderImpl) bean;
            executeOrder.setName("bbbbbbbbbb");
        }
        System.out.println("beanName = " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
