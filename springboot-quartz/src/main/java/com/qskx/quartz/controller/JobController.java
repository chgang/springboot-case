package com.qskx.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.qskx.quartz.entity.JobAndTrigger;
import com.qskx.quartz.job.BaseJob;
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

	@Autowired
	private IJobAndTriggerService iJobAndTriggerService;
	
	//加入Qulifier注解，通过名称注入bean
	@Autowired
	private Scheduler scheduler;
	
	private static Logger log = LoggerFactory.getLogger(JobController.class);

	@PostMapping(value="/addjob")
	public String addjob(@RequestParam(value="jobClassName")String jobClassName,
						 @RequestParam(value="jobGroupName")String jobGroupName,
						 @RequestParam(value="cronExpression")String cronExpression) throws Exception
	{
		log.info("addjob -> 添加新任务 jobClassName {}, jobGroupName {}, cronExpression {}", jobClassName, jobGroupName, cronExpression);
		addJob(jobClassName, jobGroupName, cronExpression);
		return "success";
	}
	
	public void addJob(String jobClassName, String jobGroupName, String cronExpression)throws Exception{
        
        // 启动调度器  
		scheduler.start(); 
		
		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
		
		//表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
            .withSchedule(scheduleBuilder).build();
        
        try {
        	scheduler.scheduleJob(jobDetail, trigger);
            
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
	}

	@PostMapping(value="/pausejob")
	public String pausejob(@RequestParam(value="jobClassName")String jobClassName,
						 @RequestParam(value="jobGroupName")String jobGroupName) throws Exception {
		log.info("pausejob -> 暂停任务 jobClassName {}, jobGroupName {}", jobClassName, jobGroupName);
		jobPause(jobClassName, jobGroupName);
		return "true";
	}
	
	public void jobPause(String jobClassName, String jobGroupName) throws Exception
	{	
		scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
	}
	

	@PostMapping(value="/resumejob")
	public String resumejob(@RequestParam(value="jobClassName")String jobClassName,
						  @RequestParam(value="jobGroupName")String jobGroupName) throws Exception {
		log.info("resumejob -> 重启任务 jobClassName {}, jobGroupName {}", jobClassName, jobGroupName);
		jobresume(jobClassName, jobGroupName);
		return "true";
	}
	
	public void jobresume(String jobClassName, String jobGroupName) throws Exception
	{
		scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
	}
	
	
	@PostMapping(value="/reschedulejob")
	public String rescheduleJob(@RequestParam(value="jobClassName")String jobClassName,
			@RequestParam(value="jobGroupName")String jobGroupName,
			@RequestParam(value="cronExpression")String cronExpression) throws Exception {
		log.info("reschedulejob -> 重置任务 jobGroupName {}, cronExpression {}", jobGroupName, cronExpression);
		jobreschedule(jobClassName, jobGroupName, cronExpression);
		return "true";
	}
	
	public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception
	{				
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			System.out.println("更新定时任务失败"+e);
			throw new Exception("更新定时任务失败");
		}
	}
	
	
	@PostMapping(value="/deletejob")
	public String deletejob(@RequestParam(value="jobClassName")String jobClassName,
						  @RequestParam(value="jobGroupName")String jobGroupName) throws Exception {
		log.info("deletejob -> 删除任务 jobClassName {}, jobGroupName {}", jobClassName, jobGroupName);
		jobdelete(jobClassName, jobGroupName);
		return "success";
	}
	
	public void jobdelete(String jobClassName, String jobGroupName) throws Exception
	{		
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));				
	}
	
	
	@GetMapping(value="/queryjob")
	public Map<String, Object> queryjob(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize)
	{
		PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JobAndTrigger", jobAndTrigger);
		map.put("number", jobAndTrigger.getTotal());
		return map;
	}
	
	public static BaseJob getClass(String classname) throws Exception
	{
		Class<?> class1 = Class.forName(classname);
		return (BaseJob)class1.newInstance();
	}
	
	
}
