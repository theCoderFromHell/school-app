package com.schoolapp.attendance.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Model Tests")
public class StudentTest {

    @Test
    @DisplayName("Should create student with all fields")
    public void testStudentCreationWithAllFields() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");

        assertEquals(1L, student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals("S001", student.getRollNumber());
        assertEquals("john@example.com", student.getEmail());
    }

    @Test
    @DisplayName("Should create student with default constructor")
    public void testStudentDefaultConstructor() {
        Student student = new Student();

        assertNull(student.getId());
        assertNull(student.getName());
        assertNull(student.getRollNumber());
        assertNull(student.getEmail());
    }

    @Test
    @DisplayName("Should set and get student properties")
    public void testStudentSettersAndGetters() {
        Student student = new Student();
        student.setId(2L);
        student.setName("Jane Smith");
        student.setRollNumber("S002");
        student.setEmail("jane@example.com");

        assertEquals(2L, student.getId());
        assertEquals("Jane Smith", student.getName());
        assertEquals("S002", student.getRollNumber());
        assertEquals("jane@example.com", student.getEmail());
    }

    @Test
    @DisplayName("Should handle null values in student")
    public void testStudentWithNullValues() {
        Student student = new Student(null, null, null, null);

        assertNull(student.getId());
        assertNull(student.getName());
        assertNull(student.getRollNumber());
        assertNull(student.getEmail());
    }

    @Test
    @DisplayName("Should support data equality")
    public void testStudentEquality() {
        Student student1 = new Student(1L, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(1L, "John Doe", "S001", "john@example.com");

        assertEquals(student1, student2);
    }

    @Test
    @DisplayName("Should support data inequality")
    public void testStudentInequality() {
        Student student1 = new Student(1L, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(2L, "Jane Smith", "S002", "jane@example.com");

        assertNotEquals(student1, student2);
    }

    @Test
    @DisplayName("Should support toString method")
    public void testStudentToString() {
        Student student = new Student(1L, "John Doe", "S001", "john@example.com");
        String result = student.toString();

        assertNotNull(result);
        assertTrue(result.contains("John Doe") || result.contains("Student"));
    }

    @Test
    @DisplayName("Should support hash code")
    public void testStudentHashCode() {
        Student student1 = new Student(1L, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(1L, "John Doe", "S001", "john@example.com");

        assertEquals(student1.hashCode(), student2.hashCode());
    }
}

