package com.example.test111.bean;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class QrtzTriggerId  implements Serializable {

  private String schedName;

  private String triggerName;

  private String triggerGroup;
}
