package com.qskx.springbootsync.lock.distribute;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.springbootsync.lock.distribute
 * @ClassName: OrderCodeGenerator
 * @Description: java类作用域描述
 * @Author: 111111
 * @CreateDate: 2019/2/27 18:48
 * @Version: 1.0
 * Copyright: Copyright (c) 2019
 */
public class OrderCodeGenerator {

    private static int i = 0;

    public String getOrderCode() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        return sdf.format(now) + ++i;
    }
}