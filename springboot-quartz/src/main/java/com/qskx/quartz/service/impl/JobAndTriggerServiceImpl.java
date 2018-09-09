package com.qskx.quartz.service.impl;

import com.qskx.quartz.dao.ScheduleJobDao;
import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.service.IJobAndTriggerService;
import com.qskx.quartz.utils.ScheduleUtils;
import com.qskx.quartz.vo.ScheduleJobVo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class JobAndTriggerServiceImpl implements IJobAndTriggerService {
	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private ScheduleJobDao scheduleJobDao;

	public void initScheduleJob() {
		ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
		List<ScheduleJobVo> scheduleJobList = scheduleJobDao.queryList(scheduleJobVo);
		if (CollectionUtils.isEmpty(scheduleJobList)) {
			return;
		}
		for (ScheduleJob scheduleJob : scheduleJobList) {

			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

			//不存在，创建一个
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				//已存在，那么更新相应的定时设置
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	public Long insert(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		return jdbcDao.insert(scheduleJob);
	}

	public void update(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		jdbcDao.update(scheduleJob);
	}

	public void delUpdate(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = new ScheduleJob();
		//先删除
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//再创建
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		//数据库直接更新即可
		jdbcDao.update(scheduleJob);
	}

	public void delete(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		//删除运行的任务
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//删除数据
		jdbcDao.delete(ScheduleJob.class, scheduleJobId);
	}

	public void runOnce(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}

	public void pauseJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//演示数据库就不更新了
	}

	public void resumeJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//演示数据库就不更新了
	}

	public ScheduleJobVo get(Long scheduleJobId) {
		ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
		return scheduleJob.getTargetObject(ScheduleJobVo.class);
	}

	public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

		List<ScheduleJob> scheduleJobs = jdbcDao.queryList(scheduleJobVo.getTargetObject(ScheduleJob.class));

		List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
		try {
			for (ScheduleJobVo vo : scheduleJobVoList) {

				JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				if (CollectionUtils.isEmpty(triggers)) {
					continue;
				}

				//这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
				Trigger trigger = triggers.iterator().next();
				vo.setJobTrigger(trigger.getKey().getName());

				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				vo.setStatus(triggerState.name());

				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					vo.setCronExpression(cronExpression);
				}
			}
		} catch (SchedulerException e) {
			//演示用，就不处理了
		}
		return scheduleJobVoList;
	}

	/**
	 * 获取运行中的job列表
	 * @return
	 */
	public List<ScheduleJobVo> queryExecutingJobList() {
		try {
			// 存放结果集
			List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>();

			// 获取scheduler中的JobGroupName
			for (String group:scheduler.getJobGroupNames()){
				// 获取JobKey 循环遍历JobKey
				for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
					JobDetail jobDetail = scheduler.getJobDetail(jobKey);
					JobDataMap jobDataMap = jobDetail.getJobDataMap();
					ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
					ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
					//TODO
//					BeanConverter.convert(scheduleJobVo,scheduleJob);
					List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
					Trigger trigger = triggers.iterator().next();
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					scheduleJobVo.setJobTrigger(trigger.getKey().getName());
					scheduleJobVo.setStatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						scheduleJobVo.setCronExpression(cronExpression);
					}
					// 获取正常运行的任务列表
					if(triggerState.name().equals("NORMAL")){
						jobList.add(scheduleJobVo);
					}
				}
			}

			/** 非集群环境获取正在执行的任务列表 */
			/**
			 List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			 List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
			 for (JobExecutionContext executingJob : executingJobs) {
			 ScheduleJobVo job = new ScheduleJobVo();
			 JobDetail jobDetail = executingJob.getJobDetail();
			 JobKey jobKey = jobDetail.getKey();
			 Trigger trigger = executingJob.getTrigger();
			 job.setJobName(jobKey.getName());
			 job.setJobGroup(jobKey.getGroup());
			 job.setJobTrigger(trigger.getKey().getName());
			 Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			 job.setStatus(triggerState.name());
			 if (trigger instanceof CronTrigger) {
			 CronTrigger cronTrigger = (CronTrigger) trigger;
			 String cronExpression = cronTrigger.getCronExpression();
			 job.setCronExpression(cronExpression);
			 }
			 jobList.add(job);
			 }*/

			return jobList;
		} catch (SchedulerException e) {
			return null;
		}
	}

	private <T> List<T> coverObjectList(Class<T> clazz, List<?> oldList, String[] ignoreProperties){
		List<T> newList = new ArrayList<>();
        oldList.stream().forEach(item -> {
            try {
                T t = clazz.newInstance();
                t = convert(t, item, ignoreProperties);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
		return newList;
	}

    public static <T> T convert(T target, Object source, String[] ignoreProperties) {
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        copySameProperties(target, source, ignoreList);
        return target;
    }

    private static void copySameProperties(Object target, Object source, List<String> ignoreList) {

	    try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();
            int length = propertyDesc.length;

            Arrays.asList(propertyDesc).stream().forEach(item -> {
                if (item.getWriteMethod() != null && (ignoreList == null || !ignoreList.contains(item.getName()))) {

                }
            });
        } catch (Exception e){

        }

	}


}