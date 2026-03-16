package com.classconnect.repository;

import com.classconnect.entity.Course;
import com.classconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    
    List<Course> findByTeacher(User teacher);
    
    List<Course> findByTeacherId(UUID teacherId);
    
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findByStudentId(@Param("studentId") UUID studentId);
    
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s = :student")
    List<Course> findByStudent(@Param("student") User student);
    
    Optional<Course> findBySubjectCode(String subjectCode);
    
    @Query("SELECT COUNT(c) FROM Course c WHERE c.teacher = :teacher")
    Long countByTeacher(@Param("teacher") User teacher);
    
    @Query("SELECT c FROM Course c WHERE c.subjectCode LIKE %:code% OR c.name LIKE %:name%")
    List<Course> findBySubjectCodeContainingOrNameContaining(@Param("code") String code, @Param("name") String name);
}

