package com.app.job_tracker.controller;

import com.app.job_tracker.dto.JobRecordDto;
import com.app.job_tracker.service.AwsService;
import com.app.job_tracker.service.JobsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/jobs")
@Validated
public class JobsController {

    @Autowired
    private JobsService jobService;

    @Autowired
    private AwsService awsService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addJobRecord(
            @RequestPart("jobRecordDto") String jobRecordDtoJson, // Accept JSON as a String
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        // Convert JSON string to JobRecordDto object
        ObjectMapper objectMapper = new ObjectMapper();
        JobRecordDto jobRecordDto = objectMapper.readValue(jobRecordDtoJson, JobRecordDto.class);

        if (!file.isEmpty()) {
            System.out.println("Resume File uploaded");
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String contentType = file.getContentType();
            long fileSize = file.getSize();
            InputStream inputStream = file.getInputStream();

            String resumeFileURL = awsService.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);
            jobRecordDto.setResumeVersion(resumeFileURL);
        }
        return ResponseEntity.ok(jobService.addJobRecord(jobRecordDto));
    }
}
