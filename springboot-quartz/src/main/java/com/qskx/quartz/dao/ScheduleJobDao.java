package com.qskx.quartz.dao;

import com.qskx.quartz.entity.ScheduleJob;
import com.qskx.quartz.vo.ScheduleJobVo;

import java.util.List;

public interface ScheduleJobDao {

	/**
	 * 新增
	 *
	 * @param scheduleJob
	 * @return
	 */
	public Long insertSelective(ScheduleJob scheduleJob);

	/**
	 * 直接修改 只能修改运行的时间，参数、同异步等无法修改
	 *
	 * @param scheduleJob
	 */
	public void updateByPrimaryKeySelective(ScheduleJob scheduleJob);

	/**
	 * 删除重新创建方式
	 *
	 * @param scheduleJob
	 */
	public void delUpdate(ScheduleJob scheduleJob);

	/**
	 * 删除
	 *
	 * @param scheduleJobId
	 */
	public void deleteByPrimaryKey(Long scheduleJobId);

	/**
	 * 运行一次任务
	 * @param scheduleJobId the schedule job id
	 * @return
	 */
	public void runOnce(Long scheduleJobId);

	/**
	 * 暂停任务
	 * @param scheduleJobId the schedule job id
	 * @return
	 */
	void pauseJob(Long scheduleJobId);

	/**
	 * 恢复任务
	 *
	 * @param scheduleJobId the schedule job id
	 * @return
	 */
	void resumeJob(Long scheduleJobId);

	/**
	 * 获取任务对象
	 *
	 * @param scheduleJobId
	 * @return
	 */
	ScheduleJob selectByPrimaryKey(Long scheduleJobId);

	/**
	 * 查询任务列表
	 *
	 * @param scheduleJob
	 * @return
	 */
	List<ScheduleJob> queryList(ScheduleJob scheduleJob);

	/**
	 * 获取运行中的任务列表
	 *
	 * @return
	 */
	public List<ScheduleJobVo> queryExecutingJobList();
}
