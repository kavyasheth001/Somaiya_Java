package com.classconnect.dto;

import com.classconnect.entity.PerformanceRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PerformanceResponse {
    
    private UUID id;
    private UserResponse student;
    private String subject;
    private PerformanceRecord.AssessmentType assessmentType;
    private Double marksObtained;
    private Double totalMarks;
    private Double percentage;
    private LocalDate recordedDate;
    private LocalDateTime createdAt;
    
    public PerformanceResponse() {}
    
    public PerformanceResponse(PerformanceRecord performanceRecord) {
        this.id = performanceRecord.getId();
        this.student = new UserResponse(performanceRecord.getStudent());
        this.subject = performanceRecord.getSubject();
        this.assessmentType = performanceRecord.getAssessmentType();
        this.marksObtained = performanceRecord.getMarksObtained();
        this.totalMarks = performanceRecord.getTotalMarks();
        this.percentage = performanceRecord.getPercentage();
        this.recordedDate = performanceRecord.getRecordedDate();
        this.createdAt = performanceRecord.getCreatedAt();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UserResponse getStudent() { return student; }
    public void setStudent(UserResponse student) { this.student = student; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public PerformanceRecord.AssessmentType getAssessmentType() { return assessmentType; }
    public void setAssessmentType(PerformanceRecord.AssessmentType assessmentType) { this.assessmentType = assessmentType; }
    
    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { this.marksObtained = marksObtained; }
    
    public Double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Double totalMarks) { this.totalMarks = totalMarks; }
    
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    
    public LocalDate getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

