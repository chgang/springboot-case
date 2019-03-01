package com.qskx.quartz.fanctory;

import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.vo.ScheduleJobVo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * @author 111111
 * @date 2018-09-08 22:23
 */
@Component
public class QuartzAsyncFactory extends QuartzJobBean implements InterruptableJob {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzAsyncFactory.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ScheduleJob scheduleJob = (ScheduleJob) jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
        LOG.info("executeInternal -> 异步任务执行时间 date = {}, 任务名称 = {}", LocalDateTime.now(), scheduleJob.getJobName());
        String jobUrl = scheduleJob.getUrl();
        String jobParam = scheduleJob.getParam();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("params", jobParam);
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LOG.info("executeInternal -> 任务名称 = {}, 任务请求参数 param = {}, 任务执行地址 url = {}", scheduleJob.getJobName(), jobParam, jobUrl);
                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestParam, headers);
                ResponseEntity<String> response = restTemplate.exchange(jobUrl, HttpMethod.POST, entity, String.class);
                String result = response.getBody();
                LOG.info("executeInternal -> 任务名称 = {}, 任务执行结果 = {}", scheduleJob.getJobName(), result);
            }
        });

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

}
