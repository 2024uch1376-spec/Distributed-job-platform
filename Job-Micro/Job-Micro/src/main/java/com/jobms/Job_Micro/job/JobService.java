package com.jobms.Job_Micro.job;

import com.jobms.Job_Micro.job.dto.JobDTO;
import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    JobDTO getJobById(Long id);
    void createJob(Job job);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}