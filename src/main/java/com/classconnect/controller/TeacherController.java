package com.classconnect.controller;

import com.classconnect.dto.CourseResponse;
import com.classconnect.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(origins = "*")
public class TeacherController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@RequestParam UUID teacherId) {
        try {
            // Get classes managed by teacher
            var classes = courseService.getCoursesByTeacher(teacherId);
            
            // Calculate stats (mock data for now)
            int classesManaged = classes.size();
            double avgAttendance = 87.5; // This would be calculated from attendance data
            double avgGrade = 82.3; // This would be calculated from performance data
            
            return ResponseEntity.ok(Map.of(
                "classesManaged", classesManaged,
                "avgAttendance", avgAttendance,
                "avgGrade", avgGrade,
                "classes", classes
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/classes")
    public ResponseEntity<?> getClasses(@RequestParam UUID teacherId) {
        try {
            var classes = courseService.getCoursesByTeacher(teacherId);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/classes/{classId}")
    public ResponseEntity<?> getClassDetails(@PathVariable UUID classId) {
        try {
            CourseResponse course = courseService.getCourseById(classId);
            // Additional details like attendance summary, assignments would be added here
            return ResponseEntity.ok(Map.of(
                "course", course,
                "totalStudents", course.getStudents().size(),
                "attendanceSummary", Map.of(
                    "present", 85,
                    "absent", 15
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

