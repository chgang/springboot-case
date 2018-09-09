package com.qskx.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.service.IJobAndTriggerService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value="/job")
public class JobController {

}
