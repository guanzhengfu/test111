package com.example.test111.bean;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@IdClass(QrtzTriggerId.class)
@Table(name = "qrtz_triggers")
@Immutable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrtzTrigger {

  @Id
  private String schedName;

  @Id
  private String triggerName;

  @Id
  private String triggerGroup;

  private String jobName;

  @Enumerated(EnumType.STRING)
  private JobGroup jobGroup;

  private Long startTime;

}
