package com.qskx.jsonrpc.server;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.server
 * @ClassName: ServerTestAPI
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 15:24
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@JsonRpcService("/calculator")
public interface ServerTestAPI {

//    @JsonRpcErrors({@JsonRpcError(exception = Exception.class, code = 501)})
    int multiplier(@JsonRpcParam(value = "a") int a, @JsonRpcParam(value = "b") int b);

}
