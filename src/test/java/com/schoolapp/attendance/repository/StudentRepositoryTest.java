package com.schoolapp.attendance.repository;

import com.schoolapp.attendance.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Student Repository Tests")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Should save and retrieve student")
    void testSaveAndRetrieveStudent() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);

        assertNotNull(saved.getId());
        assertEquals("John Doe", saved.getName());
        assertEquals("S001", saved.getRollNumber());
        assertEquals("john@example.com", saved.getEmail());
    }

    @Test
    @DisplayName("Should find student by roll number")
    void testFindByRollNumber() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        studentRepository.save(student);

        Student result = studentRepository.findByRollNumber("S001");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("S001", result.getRollNumber());
    }

    @Test
    @DisplayName("Should return null when roll number not found")
    void testFindByRollNumberNotFound() {
        Student result = studentRepository.findByRollNumber("S999");

        assertNull(result);
    }

    @Test
    @DisplayName("Should find student by ID")
    void testFindById() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);

        Optional<Student> result = studentRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
    }

    @Test
    @DisplayName("Should return empty optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Student> result = studentRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should get all students")
    void testFindAll() {
        Student student1 = new Student(null, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com");
        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> result = studentRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void testFindAllEmpty() {
        List<Student> result = studentRepository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should delete student")
    void testDeleteStudent() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);
        Long savedId = saved.getId();

        studentRepository.deleteById(savedId);
        Optional<Student> result = studentRepository.findById(savedId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should update student information")
    void testUpdateStudent() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);

        saved.setName("Jane Doe");
        saved.setEmail("jane@example.com");
        Student updated = studentRepository.save(saved);

        assertEquals("Jane Doe", updated.getName());
        assertEquals("jane@example.com", updated.getEmail());
    }

    @Test
    @DisplayName("Should handle unique roll number constraint")
    void testUniqueRollNumberConstraint() {
        Student student1 = new Student(null, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(null, "Jane Smith", "S001", "jane@example.com");
        studentRepository.save(student1);

        assertThrows(Exception.class, () -> {
            studentRepository.save(student2);
            studentRepository.flush();
        });
    }

    @Test
    @DisplayName("Should handle multiple students with different roll numbers")
    void testMultipleStudentsDifferentRollNumbers() {
        Student student1 = new Student(null, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com");
        Student student3 = new Student(null, "Bob Johnson", "S003", "bob@example.com");
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        List<Student> result = studentRepository.findAll();

        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should handle student with null email")
    void testStudentWithNullEmail() {
        Student student = new Student(null, "John Doe", "S001", null);
        Student saved = studentRepository.save(student);

        assertNotNull(saved.getId());
        assertNull(saved.getEmail());
    }

    @Test
    @DisplayName("Should handle student with special characters in name")
    void testStudentWithSpecialCharactersInName() {
        Student student = new Student(null, "John O'Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);

        assertEquals("John O'Doe", saved.getName());
    }

    @Test
    @DisplayName("Should count students")
    void testCountStudents() {
        Student student1 = new Student(null, "John Doe", "S001", "john@example.com");
        Student student2 = new Student(null, "Jane Smith", "S002", "jane@example.com");
        studentRepository.save(student1);
        studentRepository.save(student2);

        long count = studentRepository.count();

        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should check if student exists by ID")
    void testExistsById() {
        Student student = new Student(null, "John Doe", "S001", "john@example.com");
        Student saved = studentRepository.save(student);

        assertTrue(studentRepository.existsById(saved.getId()));
        assertFalse(studentRepository.existsById(999L));
    }
}

