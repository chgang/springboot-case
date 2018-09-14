package com.qskx.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.quartz.service.impl.ScheduleJobServiceImpl;
import com.qskx.quartz.vo.ScheduleJobVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value="/job")
public class JobController {

    @Autowired
    private ScheduleJobServiceImpl scheduleJobService;

    @RequestMapping(value = "/index")
    public String indexPage(){
        return "quartzlist";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String listJob(ScheduleJobVo scheduleJobVo){
        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        return JSON.toJSONString(scheduleJobVoList);
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String addJob(ScheduleJobVo scheduleJobVo, Model model){
        scheduleJobService.insert(scheduleJobVo);
        return "redirect:/job/index";
    }
}
