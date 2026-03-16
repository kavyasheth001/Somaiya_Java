package com.classconnect.service;

import com.classconnect.dto.AssignmentRequest;
import com.classconnect.dto.AssignmentResponse;
import com.classconnect.entity.Assignment;
import com.classconnect.entity.AssignmentSubmission;
import com.classconnect.entity.Course;
import com.classconnect.entity.Notification;
import com.classconnect.entity.User;
import com.classconnect.repository.AssignmentRepository;
import com.classconnect.repository.AssignmentSubmissionRepository;
import com.classconnect.repository.CourseRepository;
import com.classconnect.repository.NotificationRepository;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private AssignmentSubmissionRepository submissionRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public AssignmentResponse createAssignment(AssignmentRequest request) {
        // Find teacher
        Optional<User> teacherOptional = userRepository.findById(request.getTeacherId());
        if (teacherOptional.isEmpty() || teacherOptional.get().getRole() != User.Role.TEACHER) {
            throw new RuntimeException("Teacher not found");
        }
        
        // Find course
        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        User teacher = teacherOptional.get();
        Course course = courseOptional.get();
        
        // Create assignment
        Assignment assignment = new Assignment(request.getTitle(), request.getDescription(), 
                                            request.getSubject(), request.getDueDate(), 
                                            request.getStatus(), teacher, course);
        
        Assignment savedAssignment = assignmentRepository.save(assignment);
        
        // Create notifications for all students in the course
        createAssignmentNotifications(course, savedAssignment);
        
        return new AssignmentResponse(savedAssignment);
    }
    
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(AssignmentResponse::new)
                .collect(Collectors.toList());
    }
    
    public AssignmentResponse getAssignmentById(UUID assignmentId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            throw new RuntimeException("Assignment not found");
        }
        return new AssignmentResponse(assignmentOptional.get());
    }
    
    public List<AssignmentResponse> getAssignmentsByTeacher(UUID teacherId) {
        Optional<User> teacherOptional = userRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new RuntimeException("Teacher not found");
        }
        
        return assignmentRepository.findByTeacher(teacherOptional.get()).stream()
                .map(AssignmentResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AssignmentResponse> getAssignmentsByCourse(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        
        return assignmentRepository.findByCourse(courseOptional.get()).stream()
                .map(AssignmentResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<AssignmentResponse> getActiveAssignmentsForStudent(UUID studentId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        return assignmentRepository.findActiveAssignmentsForStudent(studentOptional.get()).stream()
                .map(AssignmentResponse::new)
                .collect(Collectors.toList());
    }
    
    public AssignmentResponse updateAssignment(UUID assignmentId, AssignmentRequest request) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            throw new RuntimeException("Assignment not found");
        }
        
        Assignment assignment = assignmentOptional.get();
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setSubject(request.getSubject());
        assignment.setDueDate(request.getDueDate());
        assignment.setStatus(request.getStatus());
        
        return new AssignmentResponse(assignmentRepository.save(assignment));
    }
    
    public void deleteAssignment(UUID assignmentId) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            throw new RuntimeException("Assignment not found");
        }
        
        assignmentRepository.delete(assignmentOptional.get());
    }
    
    public AssignmentSubmission submitAssignment(UUID assignmentId, UUID studentId, String fileUrl) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (assignmentOptional.isEmpty()) {
            throw new RuntimeException("Assignment not found");
        }
        
        Optional<User> studentOptional = userRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        
        Assignment assignment = assignmentOptional.get();
        User student = studentOptional.get();
        
        // Check if already submitted
        Optional<AssignmentSubmission> existingSubmission = submissionRepository
                .findByStudentAndAssignment(student, assignment);
        
        AssignmentSubmission submission;
        if (existingSubmission.isPresent()) {
            submission = existingSubmission.get();
            submission.setFileUrl(fileUrl);
            submission.setSubmissionDate(java.time.LocalDateTime.now());
        } else {
            submission = new AssignmentSubmission(student, assignment, fileUrl);
        }
        
        AssignmentSubmission savedSubmission = submissionRepository.save(submission);
        
        // Create notification for teacher
        createSubmissionNotification(assignment.getTeacher(), student, assignment);
        
        return savedSubmission;
    }
    
    public AssignmentSubmission gradeSubmission(UUID submissionId, Double grade, String feedback) {
        Optional<AssignmentSubmission> submissionOptional = submissionRepository.findById(submissionId);
        if (submissionOptional.isEmpty()) {
            throw new RuntimeException("Submission not found");
        }
        
        AssignmentSubmission submission = submissionOptional.get();
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        
        AssignmentSubmission savedSubmission = submissionRepository.save(submission);
        
        // Create notification for student
        createGradingNotification(submission.getStudent(), submission.getAssignment(), grade);
        
        return savedSubmission;
    }
    
    private void createAssignmentNotifications(Course course, Assignment assignment) {
        course.getStudents().forEach(student -> {
            String message = String.format("New assignment '%s' is due on %s", 
                                         assignment.getTitle(), assignment.getDueDate());
            Notification notification = new Notification(message, 
                    Notification.NotificationType.ASSIGNMENT_SUBMISSION, student);
            notificationRepository.save(notification);
        });
    }
    
    private void createSubmissionNotification(User teacher, User student, Assignment assignment) {
        String message = String.format("Student %s submitted assignment '%s'", 
                                     student.getName(), assignment.getTitle());
        Notification notification = new Notification(message, 
                Notification.NotificationType.ASSIGNMENT_SUBMISSION, teacher);
        notificationRepository.save(notification);
    }
    
    private void createGradingNotification(User student, Assignment assignment, Double grade) {
        String message = String.format("Assignment '%s' has been graded. Score: %.1f", 
                                     assignment.getTitle(), grade);
        Notification notification = new Notification(message, 
                Notification.NotificationType.ASSIGNMENT_GRADED, student);
        notificationRepository.save(notification);
    }
}

