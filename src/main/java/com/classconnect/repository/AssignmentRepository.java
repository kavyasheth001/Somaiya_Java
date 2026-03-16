package com.classconnect.repository;

import com.classconnect.entity.Assignment;
import com.classconnect.entity.Course;
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
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    
    List<Assignment> findByTeacher(User teacher);
    
    List<Assignment> findByCourse(Course course);
    
    List<Assignment> findByStatus(Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.teacher = :teacher AND a.status = :status")
    List<Assignment> findByTeacherAndStatus(@Param("teacher") User teacher, 
                                          @Param("status") Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.course = :course AND a.status = :status")
    List<Assignment> findByCourseAndStatus(@Param("course") Course course, 
                                         @Param("status") Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.subject = :subject AND a.status = :status")
    List<Assignment> findBySubjectAndStatus(@Param("subject") String subject, 
                                          @Param("status") Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.dueDate < :currentDate AND a.status = 'ACTIVE'")
    List<Assignment> findOverdueAssignments(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT a FROM Assignment a JOIN a.course c JOIN c.students s WHERE s = :student AND a.status = 'ACTIVE'")
    List<Assignment> findActiveAssignmentsForStudent(@Param("student") User student);
    
    @Query("SELECT a FROM Assignment a JOIN a.course c JOIN c.students s WHERE s = :student")
    List<Assignment> findAllAssignmentsForStudent(@Param("student") User student);
    
    @Query("SELECT a FROM Assignment a WHERE a.dueDate BETWEEN :startDate AND :endDate")
    List<Assignment> findByDueDateRange(@Param("startDate") LocalDate startDate, 
                                      @Param("endDate") LocalDate endDate);
    
    Optional<Assignment> findByIdAndTeacher(UUID id, User teacher);
}

