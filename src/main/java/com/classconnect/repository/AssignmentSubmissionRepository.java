package com.classconnect.repository;

import com.classconnect.entity.Assignment;
import com.classconnect.entity.AssignmentSubmission;
import com.classconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, UUID> {
    
    List<AssignmentSubmission> findByStudent(User student);
    
    List<AssignmentSubmission> findByAssignment(Assignment assignment);
    
    Optional<AssignmentSubmission> findByStudentAndAssignment(User student, Assignment assignment);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.assignment = :assignment AND s.grade IS NOT NULL")
    List<AssignmentSubmission> findGradedSubmissionsByAssignment(@Param("assignment") Assignment assignment);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.assignment = :assignment AND s.grade IS NULL")
    List<AssignmentSubmission> findUngradedSubmissionsByAssignment(@Param("assignment") Assignment assignment);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.student = :student AND s.grade IS NOT NULL")
    List<AssignmentSubmission> findGradedSubmissionsByStudent(@Param("student") User student);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.student = :student AND s.grade IS NULL")
    List<AssignmentSubmission> findUngradedSubmissionsByStudent(@Param("student") User student);
    
    @Query("SELECT COUNT(s) FROM AssignmentSubmission s WHERE s.assignment = :assignment")
    Long countSubmissionsByAssignment(@Param("assignment") Assignment assignment);
    
    @Query("SELECT COUNT(s) FROM AssignmentSubmission s WHERE s.assignment = :assignment AND s.grade IS NOT NULL")
    Long countGradedSubmissionsByAssignment(@Param("assignment") Assignment assignment);
    
    @Query("SELECT AVG(s.grade) FROM AssignmentSubmission s WHERE s.assignment = :assignment AND s.grade IS NOT NULL")
    Double calculateAverageGradeByAssignment(@Param("assignment") Assignment assignment);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.assignment.teacher = :teacher")
    List<AssignmentSubmission> findSubmissionsByTeacher(@Param("teacher") User teacher);
    
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.assignment.teacher = :teacher AND s.grade IS NULL")
    List<AssignmentSubmission> findUngradedSubmissionsByTeacher(@Param("teacher") User teacher);
}

