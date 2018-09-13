package com.qskx.jsonrpc.server.impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.qskx.jsonrpc.server.ServerTestAPI;
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
public class ServerTestAPIImpl implements ServerTestAPI {

    @Override
    public int multiplier(int a, int b) {
//        int i = 1/0;
        return a * b;
    }
}
