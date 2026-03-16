package com.classconnect.dto;

import com.classconnect.entity.Assignment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public class AssignmentRequest {
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    private String description;
    
    @NotBlank
    @Size(max = 100)
    private String subject;
    
    @NotNull
    private LocalDate dueDate;
    
    @NotNull
    private Assignment.AssignmentStatus status;
    
    @NotNull
    private UUID teacherId;
    
    @NotNull
    private UUID courseId;
    
    public AssignmentRequest() {}
    
    public AssignmentRequest(String title, String description, String subject, LocalDate dueDate, 
                           Assignment.AssignmentStatus status, UUID teacherId, UUID courseId) {
        this.title = title;
        this.description = description;
        this.subject = subject;
        this.dueDate = dueDate;
        this.status = status;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public Assignment.AssignmentStatus getStatus() { return status; }
    public void setStatus(Assignment.AssignmentStatus status) { this.status = status; }
    
    public UUID getTeacherId() { return teacherId; }
    public void setTeacherId(UUID teacherId) { this.teacherId = teacherId; }
    
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
}

