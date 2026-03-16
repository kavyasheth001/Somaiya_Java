package com.classconnect.controller;

import com.classconnect.dto.AttendanceRequest;
import com.classconnect.dto.AttendanceResponse;
import com.classconnect.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        try {
            var attendanceRecords = attendanceService.markAttendance(request);
            return ResponseEntity.ok(Map.of(
                "message", "Attendance marked successfully",
                "records", attendanceRecords
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAttendance(
            @RequestParam(required = false) UUID courseId,
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            if (courseId != null && date != null) {
                var attendance = attendanceService.getAttendanceByCourseAndDate(courseId, date);
                return ResponseEntity.ok(attendance);
            } else if (courseId != null) {
                var attendance = attendanceService.getAttendanceByCourse(courseId);
                return ResponseEntity.ok(attendance);
            } else if (studentId != null) {
                var attendance = attendanceService.getAttendanceByStudent(studentId);
                return ResponseEntity.ok(attendance);
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Please provide courseId, studentId, or both courseId and date"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/percentage")
    public ResponseEntity<?> getAttendancePercentage(
            @RequestParam UUID studentId,
            @RequestParam UUID courseId) {
        try {
            Double percentage = attendanceService.calculateAttendancePercentage(studentId, courseId);
            return ResponseEntity.ok(Map.of(
                "studentId", studentId,
                "courseId", courseId,
                "percentage", percentage
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<?> getAttendanceByStudentAndCourse(
            @PathVariable UUID studentId,
            @PathVariable UUID courseId) {
        try {
            var attendance = attendanceService.getAttendanceByStudentAndCourse(studentId, courseId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

