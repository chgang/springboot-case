package com.qskx.dispatcher.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description:
 * @Author: 111111
 * @CreateDate: 2018/8/17 17:17
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public class CustomDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    private Map<String, Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        //加载配置文件
        doLoadConfig(servletConfig.getInitParameter("contextConfigLocation"));

        //
    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
