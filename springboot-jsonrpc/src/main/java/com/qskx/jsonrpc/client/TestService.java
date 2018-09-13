package com.qskx.jsonrpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.client
 * @ClassName: TestService
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 15:19
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Service
public class TestService {

    @Autowired
    private ClientTestAPI clientTestAPI;

    public int multiply(int a, int b) {
        return clientTestAPI.multiplier(a, b);
    }
}
