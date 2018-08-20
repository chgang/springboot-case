package com.qskx.servletdispatcher.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: 111111
 * @CreateDate: 2018/8/17 17:04
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomRequestParam {

    String value() default "";

    boolean required() default true;
}
