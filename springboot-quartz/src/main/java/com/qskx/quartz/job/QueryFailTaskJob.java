package com.qskx.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
  
public class QueryFailTaskJob implements BaseJob {
  
    private static final Logger log = LoggerFactory.getLogger(QueryFailTaskJob.class);
     
    public QueryFailTaskJob() {
          
    }  
     
    public void execute(JobExecutionContext context)  
        throws JobExecutionException {  
        log.info("Hello Job执行时间: " + new Date());

    }  
}  
