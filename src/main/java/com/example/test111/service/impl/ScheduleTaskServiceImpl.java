package com.example.test111.service.impl;

import com.example.test111.bean.ScheduleTask;
import com.example.test111.job.MessageJob;
import com.example.test111.service.ScheduleTaskService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

  public static final String JOB_DATA_KEY = "payload";
  public static final String JOB_DESTINATION_KEY = "destination";

  private final Scheduler scheduler;

  @Override
  public void createTask(ScheduleTask task) throws SchedulerException {
    JobDetail jobDetail = JobBuilder.newJob().ofType(MessageJob.class)
        .withIdentity(task.getName(), task.getGroup())
        .usingJobData(JOB_DATA_KEY, task.getPayload())
        .usingJobData(JOB_DESTINATION_KEY, task.getDestination())
        .build();
    Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
        .withIdentity(task.getName() + "_trigger", task.getGroup())
        .startAt(new Date(task.getTaskTrigger().getStartTime()))
        .build();
    removeTask(jobDetail.getKey());
    scheduler.scheduleJob(jobDetail, trigger);
  }

  @Override
  public void removeTask(JobKey jobKey) throws SchedulerException {
    scheduler.deleteJob(jobKey);
  }
}