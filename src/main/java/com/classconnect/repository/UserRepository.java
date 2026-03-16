package com.classconnect.repository;

import com.classconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(User.Role role);
    
    List<User> findByDepartment(String department);
    
    @Query("SELECT u FROM User u WHERE u.role = 'TEACHER' AND u.department = :department")
    List<User> findTeachersByDepartment(@Param("department") String department);
    
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.department = :department")
    List<User> findStudentsByDepartment(@Param("department") String department);
    
    boolean existsByEmail(String email);
}

