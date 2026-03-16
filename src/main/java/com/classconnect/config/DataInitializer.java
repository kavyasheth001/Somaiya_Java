package com.classconnect.config;

import com.classconnect.entity.*;
import com.classconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PerformanceRecordRepository performanceRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no users exist
        if (userRepository.count() == 0) {
            initializeSampleData();
        }
    }
    
    private void initializeSampleData() {
        System.out.println("🌱 Initializing sample data...");
        
        // Create Teachers
        User teacher1 = new User("Dr. Sharma", "sharma@kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.TEACHER, "Computer Science");
        User teacher2 = new User("Prof. Kumar", "kumar@kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.TEACHER, "Computer Science");
        User teacher3 = new User("Ms. Patel", "patel@kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.TEACHER, "Computer Science");
        
        teacher1 = userRepository.save(teacher1);
        teacher2 = userRepository.save(teacher2);
        teacher3 = userRepository.save(teacher3);
        
        // Create Students
        User student1 = new User("Rahul Mehta", "rahul.mehta@student.kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.STUDENT, "Computer Science");
        User student2 = new User("Priya Singh", "priya.singh@student.kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.STUDENT, "Computer Science");
        User student3 = new User("Amit Kumar", "amit.kumar@student.kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.STUDENT, "Computer Science");
        User student4 = new User("Sneha Patel", "sneha.patel@student.kjit.edu", passwordEncoder.encode("password123"), 
                               User.Role.STUDENT, "Computer Science");
        
        student1 = userRepository.save(student1);
        student2 = userRepository.save(student2);
        student3 = userRepository.save(student3);
        student4 = userRepository.save(student4);
        
        // Create Courses
        Course course1 = new Course("Database Management Systems", "CS-301", teacher1);
        Course course2 = new Course("Data Structures", "CS-302", teacher2);
        Course course3 = new Course("Web Development", "CS-303", teacher3);
        
        course1 = courseRepository.save(course1);
        course2 = courseRepository.save(course2);
        course3 = courseRepository.save(course3);
        
        // Add students to courses
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        
        course2.addStudent(student1);
        course2.addStudent(student2);
        course2.addStudent(student4);
        
        course3.addStudent(student2);
        course3.addStudent(student3);
        course3.addStudent(student4);
        
        courseRepository.saveAll(Arrays.asList(course1, course2, course3));
        
        // Create Assignments
        Assignment assignment1 = new Assignment("Database Design Project", 
                                              "Design a complete database schema for an e-commerce platform", 
                                              "DBMS", LocalDate.now().plusDays(10), 
                                              Assignment.AssignmentStatus.ACTIVE, teacher1, course1);
        
        Assignment assignment2 = new Assignment("Binary Tree Implementation", 
                                              "Implement various binary tree operations", 
                                              "Data Structures", LocalDate.now().plusDays(15), 
                                              Assignment.AssignmentStatus.ACTIVE, teacher2, course2);
        
        Assignment assignment3 = new Assignment("Portfolio Website", 
                                              "Create a personal portfolio website using React", 
                                              "Web Development", LocalDate.now().plusDays(5), 
                                              Assignment.AssignmentStatus.ACTIVE, teacher3, course3);
        
        assignment1 = assignmentRepository.save(assignment1);
        assignment2 = assignmentRepository.save(assignment2);
        assignment3 = assignmentRepository.save(assignment3);
        
        // Create Sample Attendance Records
        Attendance attendance1 = new Attendance(course1, student1, LocalDate.now().minusDays(1), 
                                              Attendance.AttendanceStatus.PRESENT, "ER Diagrams");
        Attendance attendance2 = new Attendance(course1, student2, LocalDate.now().minusDays(1), 
                                              Attendance.AttendanceStatus.PRESENT, "ER Diagrams");
        Attendance attendance3 = new Attendance(course1, student3, LocalDate.now().minusDays(1), 
                                              Attendance.AttendanceStatus.ABSENT, "ER Diagrams");
        
        attendanceRepository.saveAll(Arrays.asList(attendance1, attendance2, attendance3));
        
        // Create Sample Performance Records
        PerformanceRecord performance1 = new PerformanceRecord(student1, "DBMS", 
                                                             PerformanceRecord.AssessmentType.MIDTERM, 85.0, 100.0, 
                                                             LocalDate.now().minusDays(30));
        PerformanceRecord performance2 = new PerformanceRecord(student2, "DBMS", 
                                                             PerformanceRecord.AssessmentType.MIDTERM, 92.0, 100.0, 
                                                             LocalDate.now().minusDays(30));
        PerformanceRecord performance3 = new PerformanceRecord(student1, "Data Structures", 
                                                             PerformanceRecord.AssessmentType.MIDTERM, 78.0, 100.0, 
                                                             LocalDate.now().minusDays(25));
        
        performanceRepository.saveAll(Arrays.asList(performance1, performance2, performance3));
        
        // Create Sample Notifications
        Notification notification1 = new Notification("New assignment 'Database Design Project' is due on " + 
                                                    LocalDate.now().plusDays(10), 
                                                    Notification.NotificationType.ASSIGNMENT_SUBMISSION, student1);
        Notification notification2 = new Notification("Your attendance is below 75% in DBMS", 
                                                    Notification.NotificationType.ATTENDANCE_ALERT, student3);
        
        notificationRepository.saveAll(Arrays.asList(notification1, notification2));
        
        System.out.println("✅ Sample data initialized successfully!");
        System.out.println("👨‍🏫 Teachers: Dr. Sharma, Prof. Kumar, Ms. Patel");
        System.out.println("👨‍🎓 Students: Rahul Mehta, Priya Singh, Amit Kumar, Sneha Patel");
        System.out.println("📚 Courses: CS-301 (DBMS), CS-302 (Data Structures), CS-303 (Web Development)");
        System.out.println("🔑 All passwords: password123");
    }
}

