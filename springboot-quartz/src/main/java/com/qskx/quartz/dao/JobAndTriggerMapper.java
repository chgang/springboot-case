package com.qskx.quartz.dao;

import java.util.List;

import com.qskx.quartz.entity.JobAndTrigger;

public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
