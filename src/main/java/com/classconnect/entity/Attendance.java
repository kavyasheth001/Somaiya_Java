package com.classconnect.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance")
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @NotNull
    @Column(name = "lecture_date")
    private LocalDate lectureDate;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private AttendanceStatus status;
    
    @Column(name = "lecture_topic")
    private String lectureTopic;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Constructors
    public Attendance() {}
    
    public Attendance(Course course, User student, LocalDate lectureDate, AttendanceStatus status, String lectureTopic) {
        this.course = course;
        this.student = student;
        this.lectureDate = lectureDate;
        this.status = status;
        this.lectureTopic = lectureTopic;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public LocalDate getLectureDate() { return lectureDate; }
    public void setLectureDate(LocalDate lectureDate) { this.lectureDate = lectureDate; }
    
    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }
    
    public String getLectureTopic() { return lectureTopic; }
    public void setLectureTopic(String lectureTopic) { this.lectureTopic = lectureTopic; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Enum for Attendance Status
    public enum AttendanceStatus {
        PRESENT, ABSENT
    }
}

