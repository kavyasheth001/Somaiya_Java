package com.classconnect.repository;

import com.classconnect.entity.PerformanceRecord;
import com.classconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PerformanceRecordRepository extends JpaRepository<PerformanceRecord, UUID> {
    
    List<PerformanceRecord> findByStudent(User student);
    
    List<PerformanceRecord> findBySubject(String subject);
    
    List<PerformanceRecord> findByAssessmentType(PerformanceRecord.AssessmentType assessmentType);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.student = :student AND p.subject = :subject")
    List<PerformanceRecord> findByStudentAndSubject(@Param("student") User student, @Param("subject") String subject);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.student = :student AND p.assessmentType = :type")
    List<PerformanceRecord> findByStudentAndAssessmentType(@Param("student") User student, 
                                                          @Param("type") PerformanceRecord.AssessmentType type);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.percentage < 60 ORDER BY p.percentage ASC")
    List<PerformanceRecord> findLowPerformers();
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.percentage >= 90 ORDER BY p.percentage DESC")
    List<PerformanceRecord> findTopPerformers();
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.student = :student ORDER BY p.percentage DESC")
    List<PerformanceRecord> findTopPerformancesByStudent(@Param("student") User student);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.student = :student ORDER BY p.percentage ASC")
    List<PerformanceRecord> findLowPerformancesByStudent(@Param("student") User student);
    
    @Query("SELECT AVG(p.percentage) FROM PerformanceRecord p WHERE p.student = :student")
    Double calculateAveragePerformanceByStudent(@Param("student") User student);
    
    @Query("SELECT AVG(p.percentage) FROM PerformanceRecord p WHERE p.student = :student AND p.subject = :subject")
    Double calculateAveragePerformanceByStudentAndSubject(@Param("student") User student, @Param("subject") String subject);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.student = :student AND p.recordedDate BETWEEN :startDate AND :endDate")
    List<PerformanceRecord> findByStudentAndDateRange(@Param("student") User student, 
                                                     @Param("startDate") LocalDate startDate, 
                                                     @Param("endDate") LocalDate endDate);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.subject = :subject ORDER BY p.percentage DESC")
    List<PerformanceRecord> findTopPerformersBySubject(@Param("subject") String subject);
    
    @Query("SELECT p FROM PerformanceRecord p WHERE p.subject = :subject ORDER BY p.percentage ASC")
    List<PerformanceRecord> findLowPerformersBySubject(@Param("subject") String subject);
}

