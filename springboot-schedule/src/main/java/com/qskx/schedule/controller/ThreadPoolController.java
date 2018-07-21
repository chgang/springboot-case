package com.qskx.schedule.controller;

import com.qskx.schedule.work.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * @author 111111
 * @date 2018-07-20 17:25
 */
@RestController
@RequestMapping("/schedule")
public class ThreadPoolController {

    @Autowired
    private ThreadPoolManager tpm;

    @RequestMapping(value = "/pool", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public Object test() {
        for (int i = 0; i < 500; i++) {
            //模拟并发500条记录
            tpm.processOrders(Integer.toString(i));
        }

        return "ok";
    }

}
