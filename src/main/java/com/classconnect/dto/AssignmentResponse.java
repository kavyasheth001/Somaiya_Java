package com.classconnect.dto;

import com.classconnect.entity.Assignment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AssignmentResponse {
    
    private UUID id;
    private String title;
    private String description;
    private String subject;
    private LocalDate dueDate;
    private Assignment.AssignmentStatus status;
    private UserResponse teacher;
    private CourseResponse course;
    private LocalDateTime createdAt;
    
    public AssignmentResponse() {}
    
    public AssignmentResponse(Assignment assignment) {
        this.id = assignment.getId();
        this.title = assignment.getTitle();
        this.description = assignment.getDescription();
        this.subject = assignment.getSubject();
        this.dueDate = assignment.getDueDate();
        this.status = assignment.getStatus();
        this.teacher = new UserResponse(assignment.getTeacher());
        this.course = new CourseResponse(assignment.getCourse());
        this.createdAt = assignment.getCreatedAt();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
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
    
    public UserResponse getTeacher() { return teacher; }
    public void setTeacher(UserResponse teacher) { this.teacher = teacher; }
    
    public CourseResponse getCourse() { return course; }
    public void setCourse(CourseResponse course) { this.course = course; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

