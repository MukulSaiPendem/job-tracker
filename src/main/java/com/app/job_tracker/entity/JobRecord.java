package com.app.job_tracker.entity;

import com.app.job_tracker.dto.JobRecordDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="jobs")
public class JobRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getResumeVersion() {
        return resumeVersion;
    }

    public void setResumeVersion(String resumeVersion) {
        this.resumeVersion = resumeVersion;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSourceOfJobListing() {
        return sourceOfJobListing;
    }

    public void setSourceOfJobListing(String sourceOfJobListing) {
        this.sourceOfJobListing = sourceOfJobListing;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getSalaryOffered() {
        return salaryOffered;
    }

    public void setSalaryOffered(String salaryOffered) {
        this.salaryOffered = salaryOffered;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String positionTitle;

    @Column(nullable = false)
    private String resumeVersion;

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate = LocalDate.now(); // Default to current date

    @Column
    private String applicationStatus = "Applied"; // Default status

    @Getter
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    // Optional fields
    private String location;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String jobDescription;
    private String sourceOfJobListing;
    private String followUpDate;
    private String interviewDate;
    private String offerDetails;
    private String notes;
    private String outcome;
    private String salaryOffered;
    private String referralSource;

    public JobRecord() {
    }
    public JobRecord(User user,String companyName, String positionTitle, String resumeVersion){
        this.user = user;
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.resumeVersion = resumeVersion;
    }

    // Method to convert DTO to entity
    public static JobRecord fromDto(JobRecordDto dto, User user) {
        JobRecord jobRecord = new JobRecord();
        jobRecord.setUser(user); // Set the user (must be fetched separately based on the DTO's userId)
        jobRecord.setCompanyName(dto.getCompanyName());
        jobRecord.setPositionTitle(dto.getPositionTitle());
        jobRecord.setResumeVersion(dto.getResumeVersion());

        // Optional fields
        Optional.ofNullable(dto.getLocation()).ifPresent(jobRecord::setLocation);
        Optional.ofNullable(dto.getContactPerson()).ifPresent(jobRecord::setContactPerson);
        Optional.ofNullable(dto.getContactEmail()).ifPresent(jobRecord::setContactEmail);
        Optional.ofNullable(dto.getContactPhone()).ifPresent(jobRecord::setContactPhone);
        Optional.ofNullable(dto.getJobDescription()).ifPresent(jobRecord::setJobDescription);
        Optional.ofNullable(dto.getSourceOfJobListing()).ifPresent(jobRecord::setSourceOfJobListing);
        Optional.ofNullable(dto.getFollowUpDate()).ifPresent(jobRecord::setFollowUpDate);
        Optional.ofNullable(dto.getInterviewDate()).ifPresent(jobRecord::setInterviewDate);
        Optional.ofNullable(dto.getOfferDetails()).ifPresent(jobRecord::setOfferDetails);
        Optional.ofNullable(dto.getNotes()).ifPresent(jobRecord::setNotes);
        Optional.ofNullable(dto.getOutcome()).ifPresent(jobRecord::setOutcome);
        Optional.ofNullable(dto.getSalaryOffered()).ifPresent(jobRecord::setSalaryOffered);
        Optional.ofNullable(dto.getReferralSource()).ifPresent(jobRecord::setReferralSource);

        return jobRecord;
    }
}
