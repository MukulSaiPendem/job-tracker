package com.app.job_tracker.service;


import com.app.job_tracker.entity.JobRecord;
import com.app.job_tracker.entity.User;
import com.app.job_tracker.dto.JobRecordDto;
import com.app.job_tracker.repository.JobsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService {

    @Autowired
    private UserService userService;

    @Autowired
    private JobsRepo jobsRepo;

    public JobRecord addJobRecord(JobRecordDto jobRecordDto) {
        User user = userService.getUserDetails();
        JobRecord jobRecord = JobRecord.fromDto(jobRecordDto, user);
        return jobsRepo.save(jobRecord);
    }
}
