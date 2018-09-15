package com.qskx.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.service.impl.ScheduleJobServiceImpl;
import com.qskx.quartz.vo.ScheduleJobVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String listJob(ScheduleJobVo scheduleJobVo){
        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        return JSON.toJSONString(scheduleJobVoList);
    }

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String addJob(ScheduleJobVo scheduleJobVo){
        scheduleJobService.insert(scheduleJobVo);
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/pause", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String pauseJob(@RequestParam(value = "id", required = true) Long scheduleJobId){
        scheduleJobService.pauseJob(scheduleJobId);
        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
        scheduleJobVo.setId(scheduleJobId);
        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        return JSON.toJSONString(scheduleJobVoList);
    }

    @RequestMapping(value = "/resume", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String resumeJob(@RequestParam(value = "id", required = true) Long scheduleJobId){
        scheduleJobService.resumeJob(scheduleJobId);
        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
        scheduleJobVo.setId(scheduleJobId);
        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        return JSON.toJSONString(scheduleJobVoList);
    }

    @RequestMapping(value = "/runOnce", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String runOnce(@RequestParam(value = "id", required = true) Long scheduleJobId){
        scheduleJobService.runOnce(scheduleJobId);
        ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
        scheduleJobVo.setId(scheduleJobId);
        List<ScheduleJobVo> scheduleJobVoList = scheduleJobService.queryList(scheduleJobVo);
        return JSON.toJSONString(scheduleJobVoList);
    }

    @RequestMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String edit(ScheduleJobVo scheduleJobVo){
        scheduleJobService.update(scheduleJobVo);
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String delete(@RequestParam(value = "id", required = true) Long scheduleJobId){
        scheduleJobService.delete(scheduleJobId);
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public String get(@RequestParam(value = "id", required = true) Long scheduleJobId){
        ScheduleJobVo scheduleJobVo =scheduleJobService.get(scheduleJobId);;
        return JSON.toJSONString(scheduleJobVo);
    }
}
