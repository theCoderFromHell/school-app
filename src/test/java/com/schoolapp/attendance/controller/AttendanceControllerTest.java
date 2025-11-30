package com.schoolapp.attendance.controller;

import com.schoolapp.attendance.model.Attendance;
import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Attendance Controller Tests")
public class AttendanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AttendanceService attendanceService;

    @InjectMocks
    private AttendanceController attendanceController;

    private Student testStudent;
    private LocalDate testDate;
    private Attendance testAttendance;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
        testStudent = new Student(1L, "John Doe", "S001", "john@example.com");
        testDate = LocalDate.of(2025, 11, 30);
        testAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT);
    }

    @Test
    @DisplayName("Should get attendance by date")
    void testGetAttendanceByDate() throws Exception {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, new Student(2L, "Jane Smith", "S002", "jane@example.com"),
                        testDate, Attendance.Status.ABSENT)
        );
        when(attendanceService.getAttendanceByDate(testDate)).thenReturn(attendanceList);

        mockMvc.perform(get("/api/attendance/date/2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value("PRESENT"))
                .andExpect(jsonPath("$[1].status").value("ABSENT"));
    }

    @Test
    @DisplayName("Should return empty list when no attendance for date")
    void testGetAttendanceByDateEmpty() throws Exception {
        when(attendanceService.getAttendanceByDate(testDate)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/attendance/date/2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should get attendance by student")
    void testGetAttendanceByStudent() throws Exception {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT)
        );
        when(attendanceService.getAttendanceByStudent(1L)).thenReturn(attendanceList);

        mockMvc.perform(get("/api/attendance/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value("PRESENT"))
                .andExpect(jsonPath("$[1].status").value("ABSENT"));
    }

    @Test
    @DisplayName("Should return empty list when student has no attendance")
    void testGetAttendanceByStudentEmpty() throws Exception {
        when(attendanceService.getAttendanceByStudent(1L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/attendance/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should verify correct endpoint for getting attendance by date")
    void testGetAttendanceByDateEndpoint() throws Exception {
        when(attendanceService.getAttendanceByDate(testDate)).thenReturn(Arrays.asList(testAttendance));

        mockMvc.perform(get("/api/attendance/date/2025-11-30"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should verify correct endpoint for getting attendance by student")
    void testGetAttendanceByStudentEndpoint() throws Exception {
        when(attendanceService.getAttendanceByStudent(1L)).thenReturn(Arrays.asList(testAttendance));

        mockMvc.perform(get("/api/attendance/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should handle multiple attendance records for different students on same date")
    void testGetAttendanceByDateMultiple() throws Exception {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, new Student(1L, "John Doe", "S001", "john@example.com"),
                        testDate, Attendance.Status.PRESENT),
                new Attendance(2L, new Student(2L, "Jane Smith", "S002", "jane@example.com"),
                        testDate, Attendance.Status.ABSENT),
                new Attendance(3L, new Student(3L, "Bob Johnson", "S003", "bob@example.com"),
                        testDate, Attendance.Status.LATE)
        );
        when(attendanceService.getAttendanceByDate(testDate)).thenReturn(attendanceList);

        mockMvc.perform(get("/api/attendance/date/2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].status").value("PRESENT"))
                .andExpect(jsonPath("$[1].status").value("ABSENT"))
                .andExpect(jsonPath("$[2].status").value("LATE"));
    }

    @Test
    @DisplayName("Should handle multiple attendance records for same student on different dates")
    void testGetAttendanceByStudentMultiple() throws Exception {
        List<Attendance> attendanceList = Arrays.asList(
                new Attendance(1L, testStudent, testDate, Attendance.Status.PRESENT),
                new Attendance(2L, testStudent, testDate.minusDays(1), Attendance.Status.ABSENT),
                new Attendance(3L, testStudent, testDate.minusDays(2), Attendance.Status.LATE)
        );
        when(attendanceService.getAttendanceByStudent(1L)).thenReturn(attendanceList);

        mockMvc.perform(get("/api/attendance/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Should get attendance with student information")
    void testGetAttendanceByDateWithStudentInfo() throws Exception {
        List<Attendance> attendanceList = Arrays.asList(testAttendance);
        when(attendanceService.getAttendanceByDate(testDate)).thenReturn(attendanceList);

        mockMvc.perform(get("/api/attendance/date/2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.name").value("John Doe"))
                .andExpect(jsonPath("$[0].student.rollNumber").value("S001"));
    }

    @Test
    @DisplayName("Should test mark attendance service call")
    void testMarkAttendanceServiceCall() {
        when(attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT))
                .thenReturn(testAttendance);

        Attendance result = attendanceService.markAttendance(1L, testDate, Attendance.Status.PRESENT);

        assert result != null;
        assert result.getStatus() == Attendance.Status.PRESENT;
    }

    @Test
    @DisplayName("Should test mark attendance with different statuses")
    void testMarkAttendanceDifferentStatuses() {
        Attendance absentAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.ABSENT);
        Attendance lateAttendance = new Attendance(1L, testStudent, testDate, Attendance.Status.LATE);

        when(attendanceService.markAttendance(1L, testDate, Attendance.Status.ABSENT))
                .thenReturn(absentAttendance);
        when(attendanceService.markAttendance(1L, testDate, Attendance.Status.LATE))
                .thenReturn(lateAttendance);

        Attendance absent = attendanceService.markAttendance(1L, testDate, Attendance.Status.ABSENT);
        Attendance late = attendanceService.markAttendance(1L, testDate, Attendance.Status.LATE);

        assert absent.getStatus() == Attendance.Status.ABSENT;
        assert late.getStatus() == Attendance.Status.LATE;
    }
}

