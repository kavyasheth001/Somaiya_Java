package com.classconnect.service;

import com.classconnect.dto.AttendanceRequest;
import com.classconnect.dto.AttendanceResponse;
import com.classconnect.entity.Attendance;
import com.classconnect.entity.Course;
import com.classconnect.entity.Notification;
import com.classconnect.entity.User;
import com.classconnect.repository.AttendanceRepository;
import com.classconnect.repository.CourseRepository;
import com.classconnect.repository.NotificationRepository;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public List<AttendanceResponse> markAttendance(AttendanceRequest request) {
        // Find course
        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        Course course = courseOptional.get();
        
        return request.getAttendanceRecords().stream()
                .map(record -> {
                    // Find student
                    Optional<User> studentOptional = userRepository.findById(record.getStudentId());
                    if (studentOptional.isEmpty() || studentOptional.get().getRole() != User.Role.STUDENT) {
                        throw new RuntimeException("Student not found");
                    }
                    
                    User student = studentOptional.get();
                    
                    // Check if attendance already exists for this date
                    Optional<Attendance> existingAttendance = attendanceRepository
                            .findByCourseAndStudentAndLectureDate(course, student, request.getLectureDate());
                    
                    Attendance attendance;
                    if (existingAttendance.isPresent()) {
                        attendance = existingAttendance.get();
                        attendance.setStatus(record.getStatus());
                    } else {
                        attendance = new Attendance(course, student, request.getLectureDate(), 
                                                  record.getStatus(), request.getLectureTopic());
                    }
                    
                    return attendanceRepository.save(attendance);
                })
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceResponse> getAttendanceByCourse(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        return attendanceRepository.findByCourse(courseOptional.get()).stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceResponse> getAttendanceByStudent(UUID studentId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return attendanceRepository.findByStudent(studentOptional.get()).stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceResponse> getAttendanceByCourseAndDate(UUID courseId, LocalDate date) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        return attendanceRepository.findByCourseAndDate(courseOptional.get(), date).stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceResponse> getAttendanceByStudentAndCourse(UUID studentId, UUID courseId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        return attendanceRepository.findByStudentAndCourse(studentOptional.get(), courseOptional.get()).stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public Double calculateAttendancePercentage(UUID studentId, UUID courseId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        Double percentage = attendanceRepository.calculateAttendancePercentage(
                studentOptional.get(), courseOptional.get());
        
        // Check if attendance is below 75% and create notification
        if (percentage != null && percentage < 75.0) {
            createAttendanceAlert(studentOptional.get(), courseOptional.get(), percentage);
        }
        
        return percentage != null ? percentage : 0.0;
    }
    
    private void createAttendanceAlert(User student, Course course, Double percentage) {
        String message = String.format("Parent notified: %s – Attendance %.1f%%", 
                                     student.getName(), percentage);
        
        Notification notification = new Notification(message, 
                Notification.NotificationType.ATTENDANCE_ALERT, student);
        notificationRepository.save(notification);
    }
}

