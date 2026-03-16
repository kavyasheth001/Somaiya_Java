package com.classconnect.dto;

import com.classconnect.entity.Course;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CourseResponse {
    
    private UUID id;
    private String name;
    private String subjectCode;
    private UserResponse teacher;
    private List<UserResponse> students;
    private LocalDateTime createdAt;
    
    public CourseResponse() {}
    
    public CourseResponse(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.subjectCode = course.getSubjectCode();
        this.teacher = new UserResponse(course.getTeacher());
        this.students = course.getStudents().stream()
                .map(UserResponse::new)
                .toList();
        this.createdAt = course.getCreatedAt();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public UserResponse getTeacher() { return teacher; }
    public void setTeacher(UserResponse teacher) { this.teacher = teacher; }
    
    public List<UserResponse> getStudents() { return students; }
    public void setStudents(List<UserResponse> students) { this.students = students; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

