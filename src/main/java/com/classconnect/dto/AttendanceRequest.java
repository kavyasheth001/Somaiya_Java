package com.classconnect.dto;

import com.classconnect.entity.Attendance;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AttendanceRequest {
    
    @NotNull
    private UUID courseId;
    
    @NotNull
    private LocalDate lectureDate;
    
    private String lectureTopic;
    
    @NotNull
    private List<AttendanceRecord> attendanceRecords;
    
    public AttendanceRequest() {}
    
    public AttendanceRequest(UUID courseId, LocalDate lectureDate, String lectureTopic, List<AttendanceRecord> attendanceRecords) {
        this.courseId = courseId;
        this.lectureDate = lectureDate;
        this.lectureTopic = lectureTopic;
        this.attendanceRecords = attendanceRecords;
    }
    
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
    
    public LocalDate getLectureDate() { return lectureDate; }
    public void setLectureDate(LocalDate lectureDate) { this.lectureDate = lectureDate; }
    
    public String getLectureTopic() { return lectureTopic; }
    public void setLectureTopic(String lectureTopic) { this.lectureTopic = lectureTopic; }
    
    public List<AttendanceRecord> getAttendanceRecords() { return attendanceRecords; }
    public void setAttendanceRecords(List<AttendanceRecord> attendanceRecords) { this.attendanceRecords = attendanceRecords; }
    
    public static class AttendanceRecord {
        @NotNull
        private UUID studentId;
        
        @NotNull
        private Attendance.AttendanceStatus status;
        
        public AttendanceRecord() {}
        
        public AttendanceRecord(UUID studentId, Attendance.AttendanceStatus status) {
            this.studentId = studentId;
            this.status = status;
        }
        
        public UUID getStudentId() { return studentId; }
        public void setStudentId(UUID studentId) { this.studentId = studentId; }
        
        public Attendance.AttendanceStatus getStatus() { return status; }
        public void setStatus(Attendance.AttendanceStatus status) { this.status = status; }
    }
}

