package com.qskx.jsonrpc.conf;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.jsonrpc.conf
 * @ClassName: ServerApplicationConfig
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/9/13 14:57
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Configuration
public class ServerApplicationConfig {
    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        AutoJsonRpcServiceImplExporter exp = new AutoJsonRpcServiceImplExporter();
        //in here you can provide custom HTTP status code providers etc. eg:
        //exp.setHttpStatusCodeProvider();
        //exp.setErrorResolver();
        return exp;
    }
}
