package com.classconnect.controller;

import com.classconnect.dto.CourseRequest;
import com.classconnect.dto.CourseResponse;
import com.classconnect.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseRequest request) {
        try {
            CourseResponse course = courseService.createCourse(request);
            return ResponseEntity.ok(Map.of(
                "message", "Course created successfully",
                "course", course
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        try {
            var courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable UUID id) {
        try {
            CourseResponse course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getCoursesByTeacher(@PathVariable UUID teacherId) {
        try {
            var courses = courseService.getCoursesByTeacher(teacherId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getCoursesByStudent(@PathVariable UUID studentId) {
        try {
            var courses = courseService.getCoursesByStudent(studentId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> addStudentToCourse(
            @PathVariable UUID courseId,
            @PathVariable UUID studentId) {
        try {
            CourseResponse course = courseService.addStudentToCourse(courseId, studentId);
            return ResponseEntity.ok(Map.of(
                "message", "Student added to course successfully",
                "course", course
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> removeStudentFromCourse(
            @PathVariable UUID courseId,
            @PathVariable UUID studentId) {
        try {
            CourseResponse course = courseService.removeStudentFromCourse(courseId, studentId);
            return ResponseEntity.ok(Map.of(
                "message", "Student removed from course successfully",
                "course", course
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

