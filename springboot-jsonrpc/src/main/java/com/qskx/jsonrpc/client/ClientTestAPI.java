package com.qskx.jsonrpc.client;

import com.googlecode.jsonrpc4j.JsonRpcParam;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.server
 * @ClassName: ClientTestAPI
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 15:10
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public interface ClientTestAPI {

    int multiplier(@JsonRpcParam(value = "a") int a, @JsonRpcParam(value = "b") int b);

}
