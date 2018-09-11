package com.qskx.quartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/job")
public class JobController {

    @RequestMapping(value = "/index")
    public String indexPage(){
        return "quartzlist";
    }

    @RequestMapping(value = "/add")
    public String addJob(){
        return "quartzlist";
    }
}
