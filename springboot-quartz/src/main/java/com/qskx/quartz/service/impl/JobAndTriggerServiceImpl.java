package com.qskx.quartz.service.impl;

import com.qskx.quartz.dao.ScheduleJobDao;
import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.service.IJobAndTriggerService;
import com.qskx.quartz.utils.ScheduleUtils;
import com.qskx.quartz.vo.ScheduleJobVo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class JobAndTriggerServiceImpl implements IJobAndTriggerService {

	private static final Logger log = LoggerFactory.getLogger(JobAndTriggerServiceImpl.class);

	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private ScheduleJobDao scheduleJobDao;

	public void initScheduleJob() {
		ScheduleJobVo scheduleJobVo = new ScheduleJobVo();

		List<ScheduleJob> scheduleJobList = scheduleJobDao.queryList(convertPOAndVo(new ScheduleJob(), scheduleJobVo, null));
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
		ScheduleJob scheduleJob = convertPOAndVo(new ScheduleJob(), scheduleJobVo, null);
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		return scheduleJobDao.insert(scheduleJob);
	}

	public void update(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = convertPOAndVo(new ScheduleJob(), scheduleJobVo, null);
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		scheduleJobDao.update(scheduleJob);
	}

	public void delUpdate(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = convertPOAndVo(new ScheduleJob(), scheduleJobVo, null);
		//先删除
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//再创建
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		//数据库直接更新即可
		scheduleJobDao.update(scheduleJob);
	}

	public void delete(Long scheduleJobId) {
		ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
		//删除运行的任务
		ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//删除数据
		scheduleJobDao.delete(scheduleJobId);
	}

	public void runOnce(Long scheduleJobId) {
		ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
		ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
	}

	public void pauseJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
		ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//演示数据库就不更新了
	}

	public void resumeJob(Long scheduleJobId) {
		ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
		ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		//演示数据库就不更新了
	}

	public ScheduleJobVo get(Long scheduleJobId) {
		ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
		return convertPOAndVo(new ScheduleJobVo(), scheduleJob, null);
	}

	public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

		List<ScheduleJob> scheduleJobs = scheduleJobDao.queryList(convertPOAndVo(new ScheduleJob(), scheduleJobVo, null));

		List<ScheduleJobVo> scheduleJobVoList = covertObjectList(ScheduleJobVo.class, scheduleJobs, null);
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
					ScheduleJobVo scheduleJobVo = convertPOAndVo(new ScheduleJobVo(), scheduleJob, null);
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

	private <T> List<T> covertObjectList(Class<T> clazz, List<?> sourceList, String[] ignoreProperties){
		List<T> targetList = new ArrayList<>();
		sourceList.stream().forEach(item -> {
            try {
                T t = clazz.newInstance();
                t = convertPOAndVo(t, item, ignoreProperties);
                targetList.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
		return targetList;
	}

    private static <T> T convertPOAndVo(T target, Object source, String[] ignoreProperties) {
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        copySameProperties(target, source, ignoreList);
        return target;
    }

    private static void copySameProperties(Object target, Object source, List<String> ignoreList) {

	    try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();

            Arrays.asList(propertyDesc).stream().forEach(item -> {
                if (item.getWriteMethod() != null && (ignoreList == null || !ignoreList.contains(item.getName()))) {
					PropertyDescriptor sourcePd = getPropertyDesByName(source.getClass(), item.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null){
						Method readMethod = sourcePd.getReadMethod();
						readMethod.setAccessible(true);
						try {
							Object value = readMethod.invoke(source);
							Method writeMethod = item.getWriteMethod();
							writeMethod.invoke(target, value);
						} catch (Exception e) {
							log.error("copySameProperties -> 获取原始对象属性值异常 error {}", e.getMessage(), e);
						}
					}
                }
            });
        } catch (Exception e){
			log.error("copySameProperties -> 同步复制属性值异常 error {}", e.getMessage(), e);
        }
	}

	private static PropertyDescriptor getPropertyDesByName(Class<?> clazz, String name){

		AtomicReference<PropertyDescriptor> resultPd = null;
		try {
			if (StringUtils.isEmpty(name)){
				return resultPd.get();
			}
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			Arrays.asList(descriptors).stream().forEach(item -> {
				if (item.getName() != null && item.getName().equals(name)){
					resultPd.set(item);
				}
			});
		} catch (Exception e){
			log.error("PropertyDescriptor -> 获取指定属性异常 error {}", e.getMessage(), e);
		}
		return resultPd.get();
	}

}