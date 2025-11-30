# 🎓 School App - Student Attendance Management System

A comprehensive Spring Boot application for managing student attendance with a RESTful API.

## 📋 Features

- **Student Management**: Add and retrieve student information
- **Attendance Tracking**: Mark and track student attendance with multiple status options (Present, Absent, Late)
- **Date-based Queries**: Retrieve attendance records by date or student
- **Automatic Updates**: Update existing attendance records for the same student and date
- **H2 In-Memory Database**: Quick setup with embedded database
- **Comprehensive Testing**: 100 test cases with 100% pass rate

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Gradle**
- **JUnit 5 & Mockito**

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Gradle (or use the included Gradle wrapper)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/theCoderFromHell/school-app.git
cd school-app
```

2. Build the project:
```bash
./gradlew clean build
```

3. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

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
