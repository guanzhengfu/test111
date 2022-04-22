package com.example.test111.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleTask {

  private String name;

  private String group;

  private String payload;

  private String destination;

  private TaskTrigger taskTrigger;
}
