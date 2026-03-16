package com.classconnect.controller;

import com.classconnect.dto.AssignmentRequest;
import com.classconnect.dto.AssignmentResponse;
import com.classconnect.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssignmentRequest request) {
        try {
            AssignmentResponse assignment = assignmentService.createAssignment(request);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment created successfully",
                "assignment", assignment
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllAssignments(
            @RequestParam(required = false) UUID teacherId,
            @RequestParam(required = false) UUID courseId,
            @RequestParam(required = false) UUID studentId) {
        try {
            if (teacherId != null) {
                var assignments = assignmentService.getAssignmentsByTeacher(teacherId);
                return ResponseEntity.ok(assignments);
            } else if (courseId != null) {
                var assignments = assignmentService.getAssignmentsByCourse(courseId);
                return ResponseEntity.ok(assignments);
            } else if (studentId != null) {
                var assignments = assignmentService.getActiveAssignmentsForStudent(studentId);
                return ResponseEntity.ok(assignments);
            } else {
                var assignments = assignmentService.getAllAssignments();
                return ResponseEntity.ok(assignments);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable UUID id) {
        try {
            AssignmentResponse assignment = assignmentService.getAssignmentById(id);
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable UUID id, @Valid @RequestBody AssignmentRequest request) {
        try {
            AssignmentResponse assignment = assignmentService.updateAssignment(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment updated successfully",
                "assignment", assignment
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable UUID id) {
        try {
            assignmentService.deleteAssignment(id);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitAssignment(
            @PathVariable UUID id,
            @RequestParam UUID studentId,
            @RequestParam String fileUrl) {
        try {
            var submission = assignmentService.submitAssignment(id, studentId, fileUrl);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment submitted successfully",
                "submission", submission
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<?> gradeSubmission(
            @PathVariable UUID submissionId,
            @RequestParam Double grade,
            @RequestParam(required = false) String feedback) {
        try {
            var submission = assignmentService.gradeSubmission(submissionId, grade, feedback);
            return ResponseEntity.ok(Map.of(
                "message", "Assignment graded successfully",
                "submission", submission
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

