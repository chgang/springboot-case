package com.qskx.quartz.fanctory;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author 111111
 * @date 2018-09-08 22:23
 */
public class QuartzAsyncFactory extends QuartzJobBean implements InterruptableJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
