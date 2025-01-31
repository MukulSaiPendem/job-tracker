package com.app.job_tracker.repository;

import com.app.job_tracker.entity.JobRecord;
import com.app.job_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobsRepo extends JpaRepository<JobRecord, UUID> {
    public List<JobRecord> findByUser(User user);
}
