package com.app.job_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JobRecordDto {

    private UUID userId; // User linking

    @NotBlank(message = "Company name is required.")
    private String companyName;
    @NotBlank(message = "Position title is required.")
    private String positionTitle;
    @NotBlank(message = "Resume version is required.")
    private String resumeVersion;

    // Optional fields
    private String location;
    private String contactPerson;
    @Email(message = "Invalid email format.")
    private String contactEmail;
    private String contactPhone;
    private String jobDescription;
    private String sourceOfJobListing;
    private String followUpDate;

    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}", // Matches format yyyy-MM-dd
            message = "Event date must be in the format yyyy-MM-dd"
    )
    private String interviewDate;

    private String offerDetails;
    private String notes;
    private String outcome;
    private String salaryOffered;
    private String referralSource;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

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

    // Default and parameterized constructors...
}
