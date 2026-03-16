package com.classconnect.service;

import com.classconnect.dto.CourseRequest;
import com.classconnect.dto.CourseResponse;
import com.classconnect.entity.Course;
import com.classconnect.entity.User;
import com.classconnect.repository.CourseRepository;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public CourseResponse createCourse(CourseRequest request) {
        // Find teacher
        Optional<User> teacherOptional = userRepository.findById(request.getTeacherId());
        if (teacherOptional.isEmpty() || teacherOptional.get().getRole() != User.Role.TEACHER) {
            throw new RuntimeException("Teacher not found");
        }
        
        User teacher = teacherOptional.get();
        
        // Create course
        Course course = new Course();
        course.setName(request.getName());
        course.setSubjectCode(request.getSubjectCode());
        course.setTeacher(teacher);
        
        Course savedCourse = courseRepository.save(course);
        
        // Add students if provided
        if (request.getStudentIds() != null && !request.getStudentIds().isEmpty()) {
            List<User> students = userRepository.findAllById(request.getStudentIds());
            students.stream()
                    .filter(student -> student.getRole() == User.Role.STUDENT)
                    .forEach(student -> savedCourse.addStudent(student));
            courseRepository.save(savedCourse);
        }
        
        return new CourseResponse(savedCourse);
    }
    
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }
    
    public CourseResponse getCourseById(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        return new CourseResponse(courseOptional.get());
    }
    
    public List<CourseResponse> getCoursesByTeacher(UUID teacherId) {
        return courseRepository.findByTeacherId(teacherId).stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<CourseResponse> getCoursesByStudent(UUID studentId) {
        return courseRepository.findByStudentId(studentId).stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList());
    }
    
    public CourseResponse addStudentToCourse(UUID courseId, UUID studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty() || studentOptional.get().getRole() != User.Role.STUDENT) {
            throw new RuntimeException("Student not found");
        }
        
        Course course = courseOptional.get();
        User student = studentOptional.get();
        
        course.addStudent(student);
        Course updatedCourse = courseRepository.save(course);
        
        return new CourseResponse(updatedCourse);
    }
    
    public CourseResponse removeStudentFromCourse(UUID courseId, UUID studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        Course course = courseOptional.get();
        User student = studentOptional.get();
        
        course.removeStudent(student);
        Course updatedCourse = courseRepository.save(course);
        
        return new CourseResponse(updatedCourse);
    }
}

