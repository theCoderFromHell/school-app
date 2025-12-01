package com.schoolapp.attendance.service;

import com.schoolapp.attendance.model.Attendance;
import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.repository.AttendanceRepository;
import com.schoolapp.attendance.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Attendance Service Tests")
public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private Student testStudent;
    private LocalDate testDate;
    private Attendance testAttendance;

    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
        testDate = LocalDate.of(2025, 11, 30);
        testAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT);
    }

    @Test
    @DisplayName("Should mark attendance successfully for new record")
    void testMarkAttendanceNew() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate)).thenReturn(Arrays.asList());
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(testAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT);

        assertNotNull(result);
        assertEquals(Attendance.Status.PRESENT, result.getStatus());
        assertEquals(testDate, result.getDate());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Should update existing attendance record")
    void testMarkAttendanceUpdate() {
        Attendance existingAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.ABSENT);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate))
                .thenReturn(Arrays.asList(existingAttendance));
        when(attendanceRepository.save(existingAttendance)).thenReturn(testAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT);

        assertNotNull(result);
        assertEquals(Attendance.Status.PRESENT, result.getStatus());
        verify(attendanceRepository, times(1)).save(existingAttendance);
    }

    @Test
    @DisplayName("Should throw exception when student not found")
    void testMarkAttendanceStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                attendanceService.markAttendance(999L, testDate, Attendance.Status.PRESENT)
        );
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get attendance by date")
    void testGetAttendanceByDate() {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, new Student(2L, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null),
                        testDate, Attendance.Status.ABSENT)
        );
        when(attendanceRepository.findByDate(testDate)).thenReturn(attendanceList);

        List<Attendance> result = attendanceService.getAttendanceByDate(testDate);

        assertEquals(2, result.size());
        verify(attendanceRepository, times(1)).findByDate(testDate);
    }

    @Test
    @DisplayName("Should return empty list when no attendance found by date")
    void testGetAttendanceByDateEmpty() {
        when(attendanceRepository.findByDate(testDate)).thenReturn(Arrays.asList());

        List<Attendance> result = attendanceService.getAttendanceByDate(testDate);

        assertTrue(result.isEmpty());
        verify(attendanceRepository, times(1)).findByDate(testDate);
    }

    @Test
    @DisplayName("Should get attendance by student")
    void testGetAttendanceByStudent() {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT)
        );
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudent(testStudent)).thenReturn(attendanceList);

        List<Attendance> result = attendanceService.getAttendanceByStudent(1L);

        assertEquals(2, result.size());
        verify(attendanceRepository, times(1)).findByStudent(testStudent);
    }

    @Test
    @DisplayName("Should return empty list when student has no attendance")
    void testGetAttendanceByStudentEmpty() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudent(testStudent)).thenReturn(Arrays.asList());

        List<Attendance> result = attendanceService.getAttendanceByStudent(1L);

        assertTrue(result.isEmpty());
        verify(attendanceRepository, times(1)).findByStudent(testStudent);
    }

    @Test
    @DisplayName("Should throw exception when getting attendance for non-existent student")
    void testGetAttendanceByStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                attendanceService.getAttendanceByStudent(999L)
        );
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should mark attendance with LATE status")
    void testMarkAttendanceWithLateStatus() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate)).thenReturn(Arrays.asList());
        Attendance lateAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.LATE);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(lateAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.LATE);

        assertEquals(Attendance.Status.LATE, result.getStatus());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Should mark attendance with ABSENT status")
    void testMarkAttendanceWithAbsentStatus() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate)).thenReturn(Arrays.asList());
        Attendance absentAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.ABSENT);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(absentAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.ABSENT);

        assertEquals(Attendance.Status.ABSENT, result.getStatus());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Should handle multiple attendance records for same student on different dates")
    void testMultipleAttendanceRecords() {
        LocalDate date2 = testDate.plusDays(1);
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, testStudent, date2, Attendance.Status.ABSENT)
        );
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudent(testStudent)).thenReturn(attendanceList);

        List<Attendance> result = attendanceService.getAttendanceByStudent(1L);

        assertEquals(2, result.size());
        assertEquals(testDate, result.get(0).getDate());
        assertEquals(date2, result.get(1).getDate());
    }

    @Test
    @DisplayName("Should verify repository calls for mark attendance new")
    void testMarkAttendanceRepositoryCalls() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate)).thenReturn(Arrays.asList());
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(testAttendance);

        attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT);

        verify(studentRepository, times(1)).findById(1L);
        verify(attendanceRepository, times(1)).findByStudentAndDate(testStudent, testDate);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    @DisplayName("Should handle updating to same status")
    void testUpdateAttendanceToSameStatus() {
        Attendance existingAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(attendanceRepository.findByStudentAndDate(testStudent, testDate))
                .thenReturn(Arrays.asList(existingAttendance));
        when(attendanceRepository.save(existingAttendance)).thenReturn(existingAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT);

        assertEquals(Attendance.Status.PRESENT, result.getStatus());
    }
}

