package com.classconnect.repository;

import com.classconnect.entity.Attendance;
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
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    
    List<Attendance> findByCourse(Course course);
    
    List<Attendance> findByStudent(User student);
    
    List<Attendance> findByCourseAndStudent(Course course, User student);
    
    @Query("SELECT a FROM Attendance a WHERE a.course = :course AND a.lectureDate = :date")
    List<Attendance> findByCourseAndDate(@Param("course") Course course, @Param("date") LocalDate date);
    
    @Query("SELECT a FROM Attendance a WHERE a.student = :student AND a.course = :course")
    List<Attendance> findByStudentAndCourse(@Param("student") User student, @Param("course") Course course);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.course = :course AND a.status = 'PRESENT'")
    Long countPresentByStudentAndCourse(@Param("student") User student, @Param("course") Course course);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = :student AND a.course = :course")
    Long countTotalByStudentAndCourse(@Param("student") User student, @Param("course") Course course);
    
    @Query("SELECT AVG(CASE WHEN a.status = 'PRESENT' THEN 100.0 ELSE 0.0 END) FROM Attendance a WHERE a.student = :student AND a.course = :course")
    Double calculateAttendancePercentage(@Param("student") User student, @Param("course") Course course);
    
    @Query("SELECT a FROM Attendance a WHERE a.course = :course AND a.lectureDate BETWEEN :startDate AND :endDate")
    List<Attendance> findByCourseAndDateRange(@Param("course") Course course, 
                                            @Param("startDate") LocalDate startDate, 
                                            @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Attendance a WHERE a.student = :student AND a.lectureDate BETWEEN :startDate AND :endDate")
    List<Attendance> findByStudentAndDateRange(@Param("student") User student, 
                                             @Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate);
    
    Optional<Attendance> findByCourseAndStudentAndLectureDate(Course course, User student, LocalDate lectureDate);
}

