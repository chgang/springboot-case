package com.qskx.quartz.annotion;

/**
 * @author 111111
 * @date 2018-10-14 13:26
 */
public @interface PermissionLimit {

    /**
     * 要求用户登录
     * @return
     */
    boolean limit() default true;

    /**
     * 要求管理员权限
     * @return
     */
    boolean adminUser() default false;
}
