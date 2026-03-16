package com.classconnect.controller;

import com.classconnect.dto.PerformanceRequest;
import com.classconnect.dto.PerformanceResponse;
import com.classconnect.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/performance")
@CrossOrigin(origins = "*")
public class PerformanceController {
    
    @Autowired
    private PerformanceService performanceService;
    
    @PostMapping
    public ResponseEntity<?> createPerformanceRecord(@Valid @RequestBody PerformanceRequest request) {
        try {
            PerformanceResponse record = performanceService.createPerformanceRecord(request);
            return ResponseEntity.ok(Map.of(
                "message", "Performance record created successfully",
                "record", record
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getPerformanceRecords(
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) String subject) {
        try {
            if (studentId != null) {
                var records = performanceService.getPerformanceByStudent(studentId);
                return ResponseEntity.ok(records);
            } else if (subject != null) {
                var records = performanceService.getPerformanceBySubject(subject);
                return ResponseEntity.ok(records);
            } else {
                var records = performanceService.getAllPerformanceRecords();
                return ResponseEntity.ok(records);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/top")
    public ResponseEntity<?> getTopPerformers() {
        try {
            var performers = performanceService.getTopPerformers();
            return ResponseEntity.ok(performers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/low")
    public ResponseEntity<?> getLowPerformers() {
        try {
            var performers = performanceService.getLowPerformers();
            return ResponseEntity.ok(performers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/export")
    public ResponseEntity<?> exportPerformanceData() {
        try {
            // Mock export functionality
            String downloadUrl = "http://localhost:8080/api/files/performance-export-" + System.currentTimeMillis() + ".csv";
            return ResponseEntity.ok(Map.of(
                "message", "Export generated successfully",
                "downloadUrl", downloadUrl,
                "format", "CSV"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/average/{studentId}")
    public ResponseEntity<?> getAveragePerformance(@PathVariable UUID studentId) {
        try {
            Double average = performanceService.getAveragePerformanceByStudent(studentId);
            return ResponseEntity.ok(Map.of(
                "studentId", studentId,
                "averagePercentage", average
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/average/{studentId}/{subject}")
    public ResponseEntity<?> getAveragePerformanceBySubject(
            @PathVariable UUID studentId,
            @PathVariable String subject) {
        try {
            Double average = performanceService.getAveragePerformanceByStudentAndSubject(studentId, subject);
            return ResponseEntity.ok(Map.of(
                "studentId", studentId,
                "subject", subject,
                "averagePercentage", average
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/range/{studentId}")
    public ResponseEntity<?> getPerformanceByDateRange(
            @PathVariable UUID studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            var records = performanceService.getPerformanceByDateRange(studentId, startDate, endDate);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerformanceRecord(@PathVariable UUID id, @Valid @RequestBody PerformanceRequest request) {
        try {
            PerformanceResponse record = performanceService.updatePerformanceRecord(id, request);
            return ResponseEntity.ok(Map.of(
                "message", "Performance record updated successfully",
                "record", record
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerformanceRecord(@PathVariable UUID id) {
        try {
            performanceService.deletePerformanceRecord(id);
            return ResponseEntity.ok(Map.of(
                "message", "Performance record deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

