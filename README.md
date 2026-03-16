<<<<<<< HEAD
# ClassConnect Backend 🎓

A comprehensive Spring Boot backend for K J Somaiya Institute of Technology's academic platform, designed to manage teachers, students, courses, assignments, attendance, and performance tracking.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Database Setup
1. Install PostgreSQL and create a database:
```sql
CREATE DATABASE classconnect;
CREATE USER classuser WITH PASSWORD 'classpass';
GRANT ALL PRIVILEGES ON DATABASE classconnect TO classuser;
```

2. Update `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classconnect
spring.datasource.username=classuser
spring.datasource.password=classpass
```

### Running the Application
```bash
# Clone the repository
git clone <repository-url>
cd classconnect-backend

# Run with Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/classconnect-backend-1.0.0.jar
```

The application will start on `http://localhost:8080`

## 📋 API Endpoints

### 🔐 Authentication
- `POST /api/auth/register` - Register new user (teacher/student)
- `POST /api/auth/login` - Login user
- `GET /api/auth/user/{userId}` - Get user details

### 🧑‍🏫 Teacher Dashboard
- `GET /api/teacher/dashboard?teacherId={id}` - Get dashboard summary
- `GET /api/teacher/classes?teacherId={id}` - Get classes taught by teacher
- `GET /api/teacher/classes/{classId}` - Get detailed class information

### 📋 Attendance Management
- `POST /api/attendance/mark` - Mark attendance for lecture
- `GET /api/attendance?courseId={id}` - Get attendance by course
- `GET /api/attendance?studentId={id}` - Get attendance by student
- `GET /api/attendance/percentage?studentId={id}&courseId={id}` - Calculate attendance percentage

### 📝 Assignment Management
- `POST /api/assignments` - Create new assignment
- `GET /api/assignments` - List all assignments (with filters)
- `GET /api/assignments/{id}` - Get assignment details
- `PUT /api/assignments/{id}` - Update assignment
- `DELETE /api/assignments/{id}` - Delete assignment
- `POST /api/assignments/{id}/submit` - Submit assignment
- `PUT /api/submissions/{id}/grade` - Grade assignment submission

### 📊 Performance Tracking
- `POST /api/performance` - Add performance record
- `GET /api/performance` - Get all performance records
- `GET /api/performance/top` - Get top performers
- `GET /api/performance/low` - Get low performers (<60%)
- `GET /api/performance/export` - Export performance data (CSV)
- `GET /api/performance/average/{studentId}` - Get student's average performance

### 🔔 Notifications
- `GET /api/notifications?userId={id}` - Get user notifications
- `GET /api/notifications/count/{userId}` - Get unread notification count
- `PUT /api/notifications/{id}/read` - Mark notification as read
- `PUT /api/notifications/mark-all-read/{userId}` - Mark all notifications as read

### 📚 Course Management
- `POST /api/courses` - Create new course
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course details
- `GET /api/courses/teacher/{teacherId}` - Get courses by teacher
- `GET /api/courses/student/{studentId}` - Get courses by student
- `POST /api/courses/{courseId}/students/{studentId}` - Add student to course
- `DELETE /api/courses/{courseId}/students/{studentId}` - Remove student from course

## 🧩 Sample Data

The application automatically creates sample data on first run:

### Teachers
- **Dr. Sharma** (sharma@kjit.edu) - Computer Science
- **Prof. Kumar** (kumar@kjit.edu) - Computer Science  
- **Ms. Patel** (patel@kjit.edu) - Computer Science

### Students
- **Rahul Mehta** (rahul.mehta@student.kjit.edu)
- **Priya Singh** (priya.singh@student.kjit.edu)
- **Amit Kumar** (amit.kumar@student.kjit.edu)
- **Sneha Patel** (sneha.patel@student.kjit.edu)

### Courses
- **CS-301**: Database Management Systems (Dr. Sharma)
- **CS-302**: Data Structures (Prof. Kumar)
- **CS-303**: Web Development (Ms. Patel)

**Default Password**: `password123` (for all users)

## 🏗️ Architecture

### Entity Relationships
```
User (Teacher/Student)
├── Course (Many-to-One with Teacher)
├── Course (Many-to-Many with Students)
├── Attendance (Many-to-One with Student)
├── Assignment (Many-to-One with Teacher)
├── AssignmentSubmission (Many-to-One with Student)
├── PerformanceRecord (Many-to-One with Student)
└── Notification (Many-to-One with Recipient)
```

### Technology Stack
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security (Basic Auth)
- **Build Tool**: Maven
- **Java Version**: 17

## 🔧 Configuration

### Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classconnect
spring.datasource.username=classuser
spring.datasource.password=classpass
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Server Configuration
```properties
server.port=8080
```

## 🚀 Features

### ✅ Implemented
- User authentication and authorization
- Course management (create, enroll students)
- Assignment creation and submission
- Attendance tracking with percentage calculation
- Performance record management
- Notification system
- Teacher and student dashboards
- RESTful API with proper error handling

### 🔄 Auto-Notifications
- Attendance alerts when percentage < 75%
- Assignment submission notifications
- Assignment grading notifications
- Assignment due date reminders

### 📊 Analytics
- Attendance percentage calculation
- Performance trend analysis
- Top and low performer identification
- Export functionality (CSV/PDF)

## 🔮 Future Enhancements

- JWT-based authentication
- Email notifications
- File upload for assignments
- Google Classroom integration
- Advanced reporting and analytics
- Real-time notifications (WebSocket)
- Mobile app support
- Grade book functionality
- Exam scheduling system

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 📝 API Documentation

Once the application is running, you can test the API endpoints using:

- **Postman Collection**: Import the provided Postman collection
- **Swagger UI**: Available at `http://localhost:8080/swagger-ui.html` (if enabled)
- **cURL Examples**: See the sample requests below

### Sample API Requests

#### Register a Teacher
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. John Doe",
    "email": "john.doe@kjit.edu",
    "password": "password123",
    "role": "TEACHER",
    "department": "Computer Science"
  }'
```

#### Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Advanced Algorithms",
    "subjectCode": "CS-401",
    "teacherId": "teacher-uuid-here",
    "studentIds": ["student1-uuid", "student2-uuid"]
  }'
```

#### Mark Attendance
```bash
curl -X POST http://localhost:8080/api/attendance/mark \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": "course-uuid-here",
    "lectureDate": "2025-01-15",
    "lectureTopic": "Binary Trees",
    "attendanceRecords": [
      {
        "studentId": "student1-uuid",
        "status": "PRESENT"
      },
      {
        "studentId": "student2-uuid",
        "status": "ABSENT"
      }
    ]
  }'
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`
   - Verify database exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or kill process using port 8080

3. **Java Version Issues**
   - Ensure Java 17+ is installed
   - Check JAVA_HOME environment variable

## 📞 Support

For issues and questions:
- Create an issue in the repository
- Contact the development team
- Check the logs in `logs/application.log`

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Happy Coding! 🎉**

Built with ❤️ for K J Somaiya Institute of Technology

=======
# Somaiya_Java
somaiya backend
>>>>>>> 0a8d0b822a45f7ecdfb2f3c3d20e5a76a004f898
