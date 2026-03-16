package com.classconnect.dto;

import com.classconnect.entity.Attendance;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AttendanceResponse {
    
    private UUID id;
    private CourseResponse course;
    private UserResponse student;
    private LocalDate lectureDate;
    private Attendance.AttendanceStatus status;
    private String lectureTopic;
    private LocalDateTime createdAt;
    
    public AttendanceResponse() {}
    
    public AttendanceResponse(Attendance attendance) {
        this.id = attendance.getId();
        this.course = new CourseResponse(attendance.getCourse());
        this.student = new UserResponse(attendance.getStudent());
        this.lectureDate = attendance.getLectureDate();
        this.status = attendance.getStatus();
        this.lectureTopic = attendance.getLectureTopic();
        this.createdAt = attendance.getCreatedAt();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public CourseResponse getCourse() { return course; }
    public void setCourse(CourseResponse course) { this.course = course; }
    
    public UserResponse getStudent() { return student; }
    public void setStudent(UserResponse student) { this.student = student; }
    
    public LocalDate getLectureDate() { return lectureDate; }
    public void setLectureDate(LocalDate lectureDate) { this.lectureDate = lectureDate; }
    
    public Attendance.AttendanceStatus getStatus() { return status; }
    public void setStatus(Attendance.AttendanceStatus status) { this.status = status; }
    
    public String getLectureTopic() { return lectureTopic; }
    public void setLectureTopic(String lectureTopic) { this.lectureTopic = lectureTopic; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

