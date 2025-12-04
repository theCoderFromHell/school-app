# 🎓 School App - Student Attendance Management System

A comprehensive Spring Boot application for managing school attendance with a RESTful API.

## 📋 Features

- **Student Management**: Add and retrieve student information
- **Attendance Tracking**: Mark and track student attendance with multiple status options (Present, Absent, Late)
- **Teacher CRUD Operations**: Complete teacher management system
- **School & Class Management**: Manage schools, classes, and sections
- **Authentication**: Spring Security with JWT and OAuth2 support
- **Date-based Queries**: Retrieve attendance records by date or student
- **MySQL Database**: Production-ready database configuration
- **Comprehensive Testing**: Extensive test coverage with JUnit 5

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security** with OAuth2
- **MySQL** (Production) / **H2** (Tests)
- **JWT** (Authentication)
- **Lombok**
- **Maven**
- **JUnit 5 & Mockito**

## 🚀 Getting Started

### Prerequisites

- Java 21 (JDK 21)
- Maven 3.9+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/theCoderFromHell/school-app.git
cd school-app
```

2. Build the project:
```bash
# Using system Maven (recommended)
mvn clean install

# Or use the helper script (sets Java 21 automatically)
./mvnw-java21.sh clean install
```

3. Run the application:
```bash
# Using system Maven
mvn spring-boot:run

# Or use the helper script
./mvnw-java21.sh spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 Documentation

Detailed documentation is available in `docs/incremental/`:

### Quick References:
- **[BUILD.md](docs/incremental/BUILD.md)** - Build instructions and troubleshooting
- **[COMPLETION_SUMMARY.md](docs/incremental/COMPLETION_SUMMARY.md)** - Project status and overview
- **[MAVEN_CONVERSION.md](docs/incremental/MAVEN_CONVERSION.md)** - Gradle to Maven conversion details
- **[JAVA_VERSION_FIX.md](docs/incremental/JAVA_VERSION_FIX.md)** - Java version configuration guide

### API Documentation:
- **[TEACHER_API_QUICK_REFERENCE.md](docs/incremental/TEACHER_API_QUICK_REFERENCE.md)** - Teacher API endpoints
- **[TEST_SUITE_DOCUMENTATION.md](docs/incremental/TEST_SUITE_DOCUMENTATION.md)** - Test coverage details

## ⚙️ Configuration

Database configuration is in `src/main/resources/application.properties`:
- **Production**: MySQL database
- **Tests**: H2 in-memory database (automatic)

## 📡 API Endpoints

### Student Endpoints

#### Get All Students
```http
GET /api/students
```

#### Add New Student
```http
POST /api/students
Content-Type: application/json

{
  "name": "John Doe",
  "rollNumber": "2024001",
  "email": "john.doe@example.com"
}
```

### Attendance Endpoints

#### Mark Attendance
```http
POST /api/attendance
Content-Type: application/json

{
  "studentId": 1,
  "date": "2024-11-30",
  "status": "PRESENT"
}
```

**Status Options**: `PRESENT`, `ABSENT`, `LATE`

#### Get Attendance by Date
```http
GET /api/attendance/date/{date}
```
Example: `GET /api/attendance/date/2024-11-30`

#### Get Attendance by Student
```http
GET /api/attendance/student/{studentId}
```
Example: `GET /api/attendance/student/1`

## 🗄️ Database

The application uses an H2 in-memory database. You can access the H2 console at:
```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## 🧪 Testing

Run all tests:
```bash
./gradlew test
```

**Test Coverage:**
- ✅ 100 test cases
- ✅ 100% pass rate
- ✅ Integration tests
- ✅ Controller tests
- ✅ Service tests
- ✅ Repository tests
- ✅ Model tests

## 📦 Project Structure

```
src/
├── main/
│   ├── java/com/schoolapp/attendance/
│   │   ├── controller/      # REST Controllers
│   │   ├── service/         # Business Logic
│   │   ├── repository/      # Data Access Layer
│   │   ├── model/           # Entity Models
│   │   └── AttendanceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/schoolapp/attendance/
        ├── controller/      # Controller Tests
        ├── service/         # Service Tests
        ├── repository/      # Repository Tests
        └── model/           # Model Tests
```

## 🔧 Configuration

Key configurations in `application.properties`:

```properties
spring.application.name=school-app
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

## 📝 License

This project is open source and available under the MIT License.

## 👨‍💻 Author

**theCoderFromHell**

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

---

Made with ❤️ using Spring Boot
