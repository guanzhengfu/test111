package com.example.test111.application;

import static com.example.test111.bean.JobGroup.PRODUCT_PUBLISHING;

import com.example.test111.bean.AutoPublishProductPayload;
import com.example.test111.bean.ScheduleTask;
import com.example.test111.bean.TaskTrigger;
import com.example.test111.service.ScheduleTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductScheduledPublishApplication {

  private final ScheduleTaskService scheduleTaskService;

  public void scheduledPublish(Long productId, Long startTime)
      throws SchedulerException, JsonProcessingException {
    AutoPublishProductPayload payload = AutoPublishProductPayload.builder()
        .productId(productId)
        .createdBy("fgz")
        .build();
    ScheduleTask scheduleTask = ScheduleTask.builder()
        .name(String.valueOf(productId))
        .group(PRODUCT_PUBLISHING.toString())
        .payload("fgz:"+productId)
        .destination("autoPublishProductHandler")
        .taskTrigger(TaskTrigger.builder().startTime(startTime).build())
        .build();
    scheduleTaskService.createTask(scheduleTask);
  }

  public void cancelScheduledPublish(Long productId) throws SchedulerException {
    scheduleTaskService.removeTask(new JobKey(String.valueOf(productId),
        PRODUCT_PUBLISHING.toString()));
  }
}
