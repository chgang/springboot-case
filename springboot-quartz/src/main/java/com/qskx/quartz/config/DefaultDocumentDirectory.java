package com.qskx.quartz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

import java.io.File;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.config
 * @ClassName: DefaultDocumentDirectory
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/26 11:53
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
//@Configuration
public class DefaultDocumentDirectory implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDocumentDirectory.class);
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        LOG.info("获取到的项目路径 path = {}", new File(System.getProperty("user.dir") + "/springboot-quartz/src/main/webapp"));
        factory.setDocumentRoot(new File(System.getProperty("user.dir") + "/springboot-quartz/src/main/webapp"));
    }

//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setDocumentRoot(new File(System.getProperty("user.dir") + "/springboot-quartz/src/main/webapp"));
//    }


}
