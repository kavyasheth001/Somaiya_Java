package com.classconnect.service;

import com.classconnect.dto.PerformanceRequest;
import com.classconnect.dto.PerformanceResponse;
import com.classconnect.entity.PerformanceRecord;
import com.classconnect.entity.User;
import com.classconnect.repository.PerformanceRecordRepository;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PerformanceService {
    
    @Autowired
    private PerformanceRecordRepository performanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public PerformanceResponse createPerformanceRecord(PerformanceRequest request) {
        // Find student
        Optional<User> studentOptional = userRepository.findById(request.getStudentId());
        if (studentOptional.isEmpty() || studentOptional.get().getRole() != User.Role.STUDENT) {
            throw new RuntimeException("Student not found");
        }
        
        User student = studentOptional.get();
        
        // Create performance record
        PerformanceRecord record = new PerformanceRecord(student, request.getSubject(), 
                                                       request.getAssessmentType(),
                                                       request.getMarksObtained(), 
                                                       request.getTotalMarks(), 
                                                       request.getRecordedDate());
        
        return new PerformanceResponse(performanceRepository.save(record));
    }
    
    public List<PerformanceResponse> getAllPerformanceRecords() {
        return performanceRepository.findAll().stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getPerformanceByStudent(UUID studentId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return performanceRepository.findByStudent(studentOptional.get()).stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getPerformanceBySubject(String subject) {
        return performanceRepository.findBySubject(subject).stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getTopPerformers() {
        return performanceRepository.findTopPerformers().stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getLowPerformers() {
        return performanceRepository.findLowPerformers().stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getTopPerformersBySubject(String subject) {
        return performanceRepository.findTopPerformersBySubject(subject).stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<PerformanceResponse> getLowPerformersBySubject(String subject) {
        return performanceRepository.findLowPerformersBySubject(subject).stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public Double getAveragePerformanceByStudent(UUID studentId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return performanceRepository.calculateAveragePerformanceByStudent(studentOptional.get());
    }
    
    public Double getAveragePerformanceByStudentAndSubject(UUID studentId, String subject) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return performanceRepository.calculateAveragePerformanceByStudentAndSubject(
                studentOptional.get(), subject);
    }
    
    public List<PerformanceResponse> getPerformanceByDateRange(UUID studentId, LocalDate startDate, LocalDate endDate) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return performanceRepository.findByStudentAndDateRange(studentOptional.get(), startDate, endDate).stream()
                .map(PerformanceResponse::new)
                .collect(Collectors.toList());
    }
    
    public PerformanceResponse updatePerformanceRecord(UUID recordId, PerformanceRequest request) {
        Optional<PerformanceRecord> recordOptional = performanceRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new RuntimeException("Performance record not found");
        }
        
        PerformanceRecord record = recordOptional.get();
        record.setSubject(request.getSubject());
        record.setAssessmentType(request.getAssessmentType());
        record.setMarksObtained(request.getMarksObtained());
        record.setTotalMarks(request.getTotalMarks());
        record.setRecordedDate(request.getRecordedDate());
        
        return new PerformanceResponse(performanceRepository.save(record));
    }
    
    public void deletePerformanceRecord(UUID recordId) {
        Optional<PerformanceRecord> recordOptional = performanceRepository.findById(recordId);
        if (recordOptional.isEmpty()) {
            throw new RuntimeException("Performance record not found");
        }
        
        performanceRepository.delete(recordOptional.get());
    }
}

