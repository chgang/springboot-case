package com.qskx.dispatcher.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: 111111
 * @CreateDate: 2018/8/17 16:45
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomRequestMapping {

    String value() default "";

    String produces() default "";
}
