package com.qskx.quartz.springbean;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.springbean
 * @ClassName: MyJobFactory
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/24 19:47
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Component
public class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception
    {
        // 首先，调用父类的方法创建好Quartz所需的Job实例
        Object jobInstance = super.createJobInstance(bundle);
        // 然后，使用BeanFactory为创建好的Job实例进行属性自动装配并将其纳入到Spring容器的管理之中，属于Spring的技术范畴.
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
