package com.qskx.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.quartz.service.KillProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: springboot-case
 * @Package: com.qskx.quartz.controller
 * @ClassName: KillJavaProcessController
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/1 18:00
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Controller
@RequestMapping("/job")
public class KillJavaProcessController {

    private static final Logger LOG = LoggerFactory.getLogger(KillJavaProcessController.class);

    @Autowired
    private KillProcessService killProcessService;

    @RequestMapping(value = "/kill/process", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String killProcess(@RequestParam(value = "programName", required = true) String programName){
        LOG.info("killProcess ==> 前台请求参数 param = {}", programName);
        return JSON.toJSONString(killProcessService.killProcess(programName));
    }
}
