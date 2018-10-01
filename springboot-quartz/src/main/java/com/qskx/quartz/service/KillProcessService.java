package com.qskx.quartz.service;

import com.qskx.quartz.utils.ResponseCode;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.service
 * @ClassName: KillProcessService
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/1 19:54
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
public interface KillProcessService {

    ResponseCode<String> killProcess(String programName);

}
