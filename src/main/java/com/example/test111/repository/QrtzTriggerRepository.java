package com.example.test111.repository;

import com.example.test111.bean.JobGroup;
import com.example.test111.bean.QrtzTrigger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrtzTriggerRepository extends JpaRepository<QrtzTrigger, Long> {

  List<QrtzTrigger> findAllByJobGroup(JobGroup jobGroup);

  QrtzTrigger findByJobGroupAndJobName(JobGroup jobGroup, String jobName);
}
