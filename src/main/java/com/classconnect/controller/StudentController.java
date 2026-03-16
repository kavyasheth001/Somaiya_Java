package com.classconnect.controller;

import com.classconnect.service.AttendanceService;
import com.classconnect.service.CourseService;
import com.classconnect.service.PerformanceService;
import com.classconnect.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private PerformanceService performanceService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@RequestParam UUID studentId) {
        try {
            // Get courses enrolled by student
            var courses = courseService.getCoursesByStudent(studentId);
            int activeSubjects = courses.size();
            
            // Calculate overall attendance percentage across all courses
            double overallAttendance = 0.0;
            if (!courses.isEmpty()) {
                double totalAttendance = 0.0;
                int coursesWithAttendance = 0;
                for (var course : courses) {
                    try {
                        Double attendance = attendanceService.calculateAttendancePercentage(studentId, course.getId());
                        if (attendance != null && attendance >= 0) {
                            totalAttendance += attendance;
                            coursesWithAttendance++;
                        }
                    } catch (Exception e) {
                        // Skip if attendance calculation fails for this course
                    }
                }
                overallAttendance = coursesWithAttendance > 0 ? totalAttendance / coursesWithAttendance : 0.0;
            }
            
            // Calculate average grade
            Double avgGrade = performanceService.getAveragePerformanceByStudent(studentId);
            if (avgGrade == null) {
                avgGrade = 0.0;
            }
            
            // Get pending assignments count (active assignments for student)
            int pendingAssignments = 0;
            try {
                var assignments = assignmentService.getActiveAssignmentsForStudent(studentId);
                pendingAssignments = assignments.size();
            } catch (Exception e) {
                // If assignment service fails, default to 0
                pendingAssignments = 0;
            }
            
            return ResponseEntity.ok(Map.of(
                "activeSubjects", activeSubjects,
                "overallAttendance", Math.round(overallAttendance * 10.0) / 10.0,
                "avgGrade", Math.round(avgGrade * 10.0) / 10.0,
                "pendingAssignments", pendingAssignments,
                "courses", courses
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

