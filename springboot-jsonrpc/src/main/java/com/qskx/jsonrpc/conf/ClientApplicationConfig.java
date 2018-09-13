package com.qskx.jsonrpc.conf;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.qskx.jsonrpc.client.ClientTestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.conf
 * @ClassName: ClientApplicationConfig
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 15:06
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Configuration
public class ClientApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ClientApplicationConfig.class);

    private static final String URL = "http://localhost:8080/calculator";
    @Bean
    public JsonRpcHttpClient jsonRpcHttpClient() {
        URL url = null;
        //You can add authentication headers etc to this map
        Map<String, String> map = new HashMap<>();
        try {
            url = new URL(ClientApplicationConfig.URL);
        } catch (Exception e) {
            log.error("JsonRpcHttpClient ==> jsonRpc 客户端请求异常 error = {}", e.getMessage(), e);
        }
        return new JsonRpcHttpClient(url, map);
    }

    @Bean
    public ClientTestAPI clientTestAPI(JsonRpcHttpClient jsonRpcHttpClient) {
        return ProxyUtil.createClientProxy(getClass().getClassLoader(), ClientTestAPI.class, jsonRpcHttpClient);
    }
}
