package com.qskx.jsonrpc.controller;

import com.qskx.jsonrpc.client.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.controller
 * @ClassName: TestJsonRpcCotroller
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 17:00
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@RestController
@RequestMapping("/json/rpc")
public class TestJsonRpcCotroller {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public Integer getResult(@RequestParam(value = "num1", required = true) Integer num1,
                             @RequestParam(value = "num2", required = true) Integer num2){
        return testService.multiply(num1, num2);
    }
}
