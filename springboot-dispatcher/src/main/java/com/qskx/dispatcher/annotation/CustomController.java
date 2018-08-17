package com.qskx.dispatcher.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: 111111
 * @CreateDate: 2018/8/17 16:40
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomController {

    /**
     * 表示给controller注册别名
     * @return
     */
    String value() default "";
}
