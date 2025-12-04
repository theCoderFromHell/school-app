package com.schoolapp.repository;

import com.schoolapp.model.Attendance;
import com.schoolapp.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Attendance Repository Tests")
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student testStudent;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testStudent = new Student(null, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
        testStudent = studentRepository.save(testStudent);
        testDate = LocalDate.of(2025, 11, 30);
    }

    @Test
    @DisplayName("Should save and retrieve attendance")
    void testSaveAndRetrieveAttendance() {
        Attendance attendance = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance saved = attendanceRepository.save(attendance);

        assertNotNull(saved.getId());
        assertEquals(Attendance.Status.PRESENT, saved.getStatus());
        assertEquals(testDate, saved.getDate());
    }

    @Test
    @DisplayName("Should find attendance by student")
    void testFindByStudent() {
        Attendance attendance1 = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(null, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT);
        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        List<Attendance> result = attendanceRepository.findByStudent(testStudent);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should find attendance by date")
    void testFindByDate() {
        Attendance attendance1 = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null);
        student2 = studentRepository.save(student2);
        Attendance attendance2 = new Attendance(null, student2, testDate, Attendance.Status.ABSENT);
        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        List<Attendance> result = attendanceRepository.findByDate(testDate);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should find attendance by student and date")
    void testFindByStudentAndDate() {
        Attendance attendance = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        attendanceRepository.save(attendance);

        List<Attendance> result = attendanceRepository.findByStudentAndDate(testStudent, testDate);

        assertEquals(1, result.size());
        assertEquals(Attendance.Status.PRESENT, result.get(0).getStatus());
    }

    @Test
    @DisplayName("Should return empty list when no attendance found by student")
    void testFindByStudentEmpty() {
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null);
        student2 = studentRepository.save(student2);

        List<Attendance> result = attendanceRepository.findByStudent(student2);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no attendance found by date")
    void testFindByDateEmpty() {
        LocalDate futureDate = testDate.plusDays(10);

        List<Attendance> result = attendanceRepository.findByDate(futureDate);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no attendance found by student and date")
    void testFindByStudentAndDateEmpty() {
        List<Attendance> result = attendanceRepository.findByStudentAndDate(testStudent, testDate.plusDays(1));

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should delete attendance")
    void testDeleteAttendance() {
        Attendance attendance = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance saved = attendanceRepository.save(attendance);
        Long savedId = saved.getId();

        attendanceRepository.deleteById(savedId);
        Optional<Attendance> result = attendanceRepository.findById(savedId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should update attendance status")
    void testUpdateAttendanceStatus() {
        Attendance attendance = new Attendance(null, testStudent, testDate, Attendance.Status.ABSENT);
        Attendance saved = attendanceRepository.save(attendance);

        saved.setStatus(Attendance.Status.PRESENT);
        Attendance updated = attendanceRepository.save(saved);

        assertEquals(Attendance.Status.PRESENT, updated.getStatus());
    }

    @Test
    @DisplayName("Should handle multiple attendance records for different dates")
    void testMultipleAttendanceDifferentDates() {
        Attendance attendance1 = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(null, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT);
        Attendance attendance3 = new Attendance(null, testStudent, testDate.minusDays(2), Attendance.Status.LATE);
        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);
        attendanceRepository.save(attendance3);

        List<Attendance> result = attendanceRepository.findByStudent(testStudent);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should handle multiple attendance records for same date different students")
    void testMultipleAttendanceDifferentStudents() {
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null);
        Student student3 = new Student(null, "Bob Johnson", "S003", "bob@example.com", null, "5555555555", "789 Elm St", null);
        student2 = studentRepository.save(student2);
        student3 = studentRepository.save(student3);

        Attendance attendance1 = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(null, student2, testDate, Attendance.Status.ABSENT);
        Attendance attendance3 = new Attendance(null, student3, testDate, Attendance.Status.LATE);
        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);
        attendanceRepository.save(attendance3);

        List<Attendance> result = attendanceRepository.findByDate(testDate);

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should find by ID")
    void testFindById() {
        Attendance attendance = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance saved = attendanceRepository.save(attendance);

        Optional<Attendance> result = attendanceRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Attendance> result = attendanceRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should get all attendance records")
    void testFindAll() {
        Attendance attendance1 = new Attendance(null, testStudent, testDate, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(null, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT);
        attendanceRepository.save(attendance1);
        attendanceRepository.save(attendance2);

        List<Attendance> result = attendanceRepository.findAll();

        assertEquals(2, result.size());
    }
}

