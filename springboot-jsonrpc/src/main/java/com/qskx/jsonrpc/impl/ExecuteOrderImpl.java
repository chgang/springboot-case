package com.qskx.jsonrpc.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.springbootnio.impl
 * @ClassName: ExecuteOrderImpl
 * @Description: java类作用域描述
 * @Author: 111111
 * @CreateDate: 2019/2/9 21:07
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
@Component
public class ExecuteOrderImpl implements InitializingBean {

    public ExecuteOrderImpl () {
        System.out.println(">>>>>>>>>>> 构造方法执行了!!!");
    }

    private String name;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(">>>>>>>>>>>> InitializingBean 执行了！！！");
    }
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        this.name = "aaa";
//        System.out.println(">>>>>>>>>>> BeanFactoryPostProcessor 执行了！！！");
//    }


//    @PostConstruct
//    public void init() {
//        System.out.println("PostConstruct 执行了！！！");
//    }

    public void testOrder() {
        System.out.println("aaaa--bbbb");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}