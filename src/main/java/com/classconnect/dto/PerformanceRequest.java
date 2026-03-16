package com.classconnect.dto;

import com.classconnect.entity.PerformanceRecord;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public class PerformanceRequest {
    
    @NotNull
    private UUID studentId;
    
    @NotNull
    @Size(max = 100)
    private String subject;
    
    @NotNull
    private PerformanceRecord.AssessmentType assessmentType;
    
    @NotNull
    @DecimalMin(value = "0.0")
    private Double marksObtained;
    
    @NotNull
    @DecimalMin(value = "0.0")
    private Double totalMarks;
    
    @NotNull
    private LocalDate recordedDate;
    
    public PerformanceRequest() {}
    
    public PerformanceRequest(UUID studentId, String subject, PerformanceRecord.AssessmentType assessmentType, 
                            Double marksObtained, Double totalMarks, LocalDate recordedDate) {
        this.studentId = studentId;
        this.subject = subject;
        this.assessmentType = assessmentType;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.recordedDate = recordedDate;
    }
    
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public PerformanceRecord.AssessmentType getAssessmentType() { return assessmentType; }
    public void setAssessmentType(PerformanceRecord.AssessmentType assessmentType) { this.assessmentType = assessmentType; }
    
    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { this.marksObtained = marksObtained; }
    
    public Double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Double totalMarks) { this.totalMarks = totalMarks; }
    
    public LocalDate getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
}

