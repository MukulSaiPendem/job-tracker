package com.app.job_tracker.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.region.static}")
    private String region;

    // Method to upload a file to an S3 bucket
    public String uploadFile(
            final String bucketName,
            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) throws AmazonClientException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);

        // Upload file to S3
        s3Client.putObject(bucketName, keyName, value, metadata);

        // Construct file URL
        String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + keyName;

        System.out.println("File uploaded to: " + fileUrl);
        return fileUrl;
    }

    // Method to download a file from an S3 bucket
    public ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) throws IOException, AmazonClientException {
        S3Object s3Object = s3Client.getObject(bucketName, keyName);
        InputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        System.out.println("File downloaded from bucket({}): {} " + bucketName + keyName);
        return outputStream;
    }

    // Method to list files in an S3 bucket
    public List<String> listFiles(final String bucketName) throws AmazonClientException {
        List<String> keys = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            objectSummaries.stream()
                    .filter(item -> !item.getKey().endsWith("/"))
                    .map(S3ObjectSummary::getKey)
                    .forEach(keys::add);

            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }

        System.out.println("Files found in bucket({}): {}" +  bucketName + keys);
        return keys;
    }

    // Method to delete a file from an S3 bucket
    public void deleteFile(
            final String bucketName,
            final String keyName
    ) throws AmazonClientException {
        s3Client.deleteObject(bucketName, keyName);
        System.out.println("File deleted from bucket({}): {}" + bucketName + keyName);
    }
}
