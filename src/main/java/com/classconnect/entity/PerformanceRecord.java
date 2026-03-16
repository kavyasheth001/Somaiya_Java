package com.classconnect.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "performance_records")
public class PerformanceRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @NotNull
    @Size(max = 100)
    private String subject;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "assessment_type")
    private AssessmentType assessmentType;
    
    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "marks_obtained")
    private Double marksObtained;
    
    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "total_marks")
    private Double totalMarks;
    
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double percentage;
    
    @NotNull
    @Column(name = "recorded_date")
    private LocalDate recordedDate;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Constructors
    public PerformanceRecord() {}
    
    public PerformanceRecord(User student, String subject, AssessmentType assessmentType, 
                           Double marksObtained, Double totalMarks, LocalDate recordedDate) {
        this.student = student;
        this.subject = subject;
        this.assessmentType = assessmentType;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.percentage = (marksObtained / totalMarks) * 100;
        this.recordedDate = recordedDate;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public AssessmentType getAssessmentType() { return assessmentType; }
    public void setAssessmentType(AssessmentType assessmentType) { this.assessmentType = assessmentType; }
    
    public Double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Double marksObtained) { 
        this.marksObtained = marksObtained;
        if (totalMarks != null && totalMarks > 0) {
            this.percentage = (marksObtained / totalMarks) * 100;
        }
    }
    
    public Double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Double totalMarks) { 
        this.totalMarks = totalMarks;
        if (marksObtained != null && totalMarks > 0) {
            this.percentage = (marksObtained / totalMarks) * 100;
        }
    }
    
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    
    public LocalDate getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Enum for Assessment Type
    public enum AssessmentType {
        MIDTERM, FINAL, INTERNAL, ASSIGNMENT, QUIZ
    }
}

