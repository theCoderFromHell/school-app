package com.schoolapp.attendance.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Attendance Model Tests")
public class AttendanceTest {

    @Test
    @DisplayName("Should create attendance with all fields")
    public void testAttendanceCreationWithAllFields() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance = new Attendance(1L, student, date, Attendance.Status.PRESENT);

        assertEquals(1L, attendance.getId());
        assertEquals(student, attendance.getStudent());
        assertEquals(date, attendance.getDate());
        assertEquals(Attendance.Status.PRESENT, attendance.getStatus());
    }

    @Test
    @DisplayName("Should create attendance with default constructor")
    public void testAttendanceDefaultConstructor() {
        Attendance attendance = new Attendance();

        assertNull(attendance.getId());
        assertNull(attendance.getStudent());
        assertNull(attendance.getDate());
        assertNull(attendance.getStatus());
    }

    @Test
    @DisplayName("Should set and get attendance properties")
    public void testAttendanceSettersAndGetters() {
        Attendance attendance = new Attendance();
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);

        attendance.setId(2L);
        attendance.setStudent(student);
        attendance.setDate(date);
        attendance.setStatus(Attendance.Status.ABSENT);

        assertEquals(2L, attendance.getId());
        assertEquals(student, attendance.getStudent());
        assertEquals(date, attendance.getDate());
        assertEquals(Attendance.Status.ABSENT, attendance.getStatus());
    }

    @Test
    @DisplayName("Should handle all status types")
    public void testAttendanceStatusTypes() {
        Attendance attendance = new Attendance();

        attendance.setStatus(Attendance.Status.PRESENT);
        assertEquals(Attendance.Status.PRESENT, attendance.getStatus());

        attendance.setStatus(Attendance.Status.ABSENT);
        assertEquals(Attendance.Status.ABSENT, attendance.getStatus());

        attendance.setStatus(Attendance.Status.LATE);
        assertEquals(Attendance.Status.LATE, attendance.getStatus());
    }

    @Test
    @DisplayName("Should support Status enum valueOf")
    public void testStatusEnumValueOf() {
        assertEquals(Attendance.Status.PRESENT, Attendance.Status.valueOf("PRESENT"));
        assertEquals(Attendance.Status.ABSENT, Attendance.Status.valueOf("ABSENT"));
        assertEquals(Attendance.Status.LATE, Attendance.Status.valueOf("LATE"));
    }

    @Test
    @DisplayName("Should support Status enum values")
    public void testStatusEnumValues() {
        Attendance.Status[] statuses = Attendance.Status.values();
        assertEquals(3, statuses.length);
    }

    @Test
    @DisplayName("Should support data equality")
    public void testAttendanceEquality() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance1 = new Attendance(1L, student, date, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(1L, student, date, Attendance.Status.PRESENT);

        assertEquals(attendance1, attendance2);
    }

    @Test
    @DisplayName("Should support data inequality")
    public void testAttendanceInequality() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance1 = new Attendance(1L, student, date, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(2L, student, date, Attendance.Status.ABSENT);

        assertNotEquals(attendance1, attendance2);
    }

    @Test
    @DisplayName("Should support toString method")
    public void testAttendanceToString() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance = new Attendance(1L, student, date, Attendance.Status.PRESENT);
        String result = attendance.toString();

        assertNotNull(result);
        assertTrue(result.contains("PRESENT") || result.contains("Attendance"));
    }

    @Test
    @DisplayName("Should support hash code")
    public void testAttendanceHashCode() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance1 = new Attendance(1L, student, date, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(1L, student, date, Attendance.Status.PRESENT);

        assertEquals(attendance1.hashCode(), attendance2.hashCode());
    }

    @Test
    @DisplayName("Should handle null student in attendance")
    public void testAttendanceWithNullStudent() {
        LocalDate date = LocalDate.of(2025, 11, 30);
        Attendance attendance = new Attendance(1L, null, date, Attendance.Status.PRESENT);

        assertNull(attendance.getStudent());
        assertEquals(date, attendance.getDate());
    }

    @Test
    @DisplayName("Should handle different dates")
    public void testAttendanceWithDifferentDates() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        LocalDate date1 = LocalDate.of(2025, 11, 29);
        LocalDate date2 = LocalDate.of(2025, 11, 30);

        Attendance attendance1 = new Attendance(1L, student, date1, Attendance.Status.PRESENT);
        Attendance attendance2 = new Attendance(2L, student, date2, Attendance.Status.PRESENT);

        assertNotEquals(attendance1, attendance2);
    }
}

