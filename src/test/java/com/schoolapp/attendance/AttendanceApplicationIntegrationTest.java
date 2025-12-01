package com.schoolapp.attendance;

import com.schoolapp.attendance.model.Attendance;
import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.repository.AttendanceRepository;
import com.schoolapp.attendance.repository.StudentRepository;
import com.schoolapp.attendance.service.AttendanceService;
import com.schoolapp.attendance.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Attendance Application Integration Tests")
public class AttendanceApplicationIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @BeforeEach
    void setUp() {
        attendanceRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create student and mark attendance")
    void testCreateStudentAndMarkAttendance() {
        // Create student
        Student student = new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
        Student savedStudent = studentService.addStudent(student);

        assertNotNull(savedStudent.getId());

        // Mark attendance
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance = attendanceService.markAttendance(
                savedStudent.getId(), date, Attendance.Status.PRESENT
        );

        assertNotNull(attendance);
        assertEquals(Attendance.Status.PRESENT, attendance.getStatus());
    }

    @Test
    @DisplayName("Should retrieve student and their attendance")
    void testRetrieveStudentAndAttendance() {
        // Create student
        Student student = new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
        Student savedStudent = studentService.addStudent(student);

        // Mark attendance
        LocalDate date = LocalDate.of(2025, 11, 30);
        attendanceService.markAttendance(savedStudent.getId(), date, Attendance.Status.PRESENT);

        // Retrieve attendance
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(savedStudent.getId());

        assertEquals(1, attendanceList.size());
        assertEquals(Attendance.Status.PRESENT, attendanceList.get(0).getStatus());
    }

    @Test
    @DisplayName("Should get attendance by date")
    void testGetAttendanceByDate() {
        // Create students
        Student student1 = studentService.addStudent(
                new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null)
        );
        Student student2 = studentService.addStudent(
                new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null)
        );

        // Mark attendance
        LocalDate date = LocalDate.of(2025, 11, 30);
        attendanceService.markAttendance(student1.getId(), date, Attendance.Status.PRESENT);
        attendanceService.markAttendance(student2.getId(), date, Attendance.Status.ABSENT);

        // Get attendance by date
        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(date);

        assertEquals(2, attendanceList.size());
    }

    @Test
    @DisplayName("Should update attendance status")
    void testUpdateAttendanceStatus() {
        // Create student
        Student student = studentService.addStudent(
                new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null)
        );

        // Mark attendance as absent
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance1 = attendanceService.markAttendance(
                student.getId(), date, Attendance.Status.ABSENT
        );
        assertEquals(Attendance.Status.ABSENT, attendance1.getStatus());

        // Update to present
        Attendance attendance2 = attendanceService.markAttendance(
                student.getId(), date, Attendance.Status.PRESENT
        );
        assertEquals(Attendance.Status.PRESENT, attendance2.getStatus());
    }

    @Test
    @DisplayName("Should get multiple students with attendance")
    void testMultipleStudentsWithAttendance() {
        // Create multiple students
        Student student1 = studentService.addStudent(
                new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null)
        );
        Student student2 = studentService.addStudent(
                new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null)
        );
        Student student3 = studentService.addStudent(
                new Student(null, "Bob Johnson", "S003", "bob@example.com", null, "5555555555", "789 Elm St", null)
        );

        // Get all students
        List<Student> students = studentService.getAllStudents();
        assertEquals(3, students.size());

        // Mark attendance for all
        LocalDate date = LocalDate.of(2025, 11, 30);
        attendanceService.markAttendance(student1.getId(), date, Attendance.Status.PRESENT);
        attendanceService.markAttendance(student2.getId(), date, Attendance.Status.ABSENT);
        attendanceService.markAttendance(student3.getId(), date, Attendance.Status.LATE);

        // Get attendance by date
        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(date);
        assertEquals(3, attendanceList.size());
    }

    @Test
    @DisplayName("Should handle multiple dates for same student")
    void testMultipleDatesForSameStudent() {
        // Create student
        Student student = studentService.addStudent(
                new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null)
        );

        // Mark attendance for multiple dates
        LocalDate date1 = LocalDate.of(2025, 11, 28);
        LocalDate date2 = LocalDate.of(2025, 11, 29);
        LocalDate date3 = LocalDate.of(2025, 11, 30);

        attendanceService.markAttendance(student.getId(), date1, Attendance.Status.PRESENT);
        attendanceService.markAttendance(student.getId(), date2, Attendance.Status.ABSENT);
        attendanceService.markAttendance(student.getId(), date3, Attendance.Status.LATE);

        // Get all attendance for student
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(student.getId());
        assertEquals(3, attendanceList.size());
    }

    @Test
    @DisplayName("Should retrieve student by roll number")
    void testGetStudentByRollNumber() {
        // Create student
        Student student = new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
        studentService.addStudent(student);

        // Retrieve by roll number
        Student retrieved = studentService.getStudentByRollNumber("S001");

        assertNotNull(retrieved);
        assertEquals("John Doe", retrieved.getName());
        assertEquals("S001", retrieved.getRollNumber());
    }

    @Test
    @DisplayName("Should handle complex scenario with multiple students and dates")
    void testComplexScenario() {
        // Create students
        Student student1 = studentService.addStudent(
                new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null)
        );
        Student student2 = studentService.addStudent(
                new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null)
        );

        // Mark attendance for multiple dates
        LocalDate date1 = LocalDate.of(2025, 11, 28);
        LocalDate date2 = LocalDate.of(2025, 11, 29);
        LocalDate date3 = LocalDate.of(2025, 11, 30);

        // Student 1 attendance
        attendanceService.markAttendance(student1.getId(), date1, Attendance.Status.PRESENT);
        attendanceService.markAttendance(student1.getId(), date2, Attendance.Status.PRESENT);
        attendanceService.markAttendance(student1.getId(), date3, Attendance.Status.ABSENT);

        // Student 2 attendance
        attendanceService.markAttendance(student2.getId(), date1, Attendance.Status.ABSENT);
        attendanceService.markAttendance(student2.getId(), date2, Attendance.Status.LATE);
        attendanceService.markAttendance(student2.getId(), date3, Attendance.Status.PRESENT);

        // Verify student 1 records
        List<Attendance> student1Attendance = attendanceService.getAttendanceByStudent(student1.getId());
        assertEquals(3, student1Attendance.size());

        // Verify student 2 records
        List<Attendance> student2Attendance = attendanceService.getAttendanceByStudent(student2.getId());
        assertEquals(3, student2Attendance.size());

        // Verify date records
        List<Attendance> date1Attendance = attendanceService.getAttendanceByDate(date1);
        assertEquals(2, date1Attendance.size());

        List<Attendance> date2Attendance = attendanceService.getAttendanceByDate(date2);
        assertEquals(2, date2Attendance.size());

        List<Attendance> date3Attendance = attendanceService.getAttendanceByDate(date3);
        assertEquals(2, date3Attendance.size());
    }

    @Test
    @DisplayName("Should throw exception when marking attendance for non-existent student")
    void testMarkAttendanceForNonExistentStudent() {
        LocalDate date = LocalDate.of(2025, 11, 30);

        assertThrows(RuntimeException.class, () ->
                attendanceService.markAttendance(999L, date, Attendance.Status.PRESENT)
        );
    }

    @Test
    @DisplayName("Should handle empty attendance list for new date")
    void testEmptyAttendanceForNewDate() {
        LocalDate futureDate = LocalDate.of(2026, 12, 25);

        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(futureDate);

        assertTrue(attendanceList.isEmpty());
    }
}

