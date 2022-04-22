package com.example.test111.service;

import com.example.test111.bean.ScheduleTask;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

public interface ScheduleTaskService {

  void createTask(ScheduleTask task) throws SchedulerException;

  void removeTask(JobKey jobKey) throws SchedulerException;

}
