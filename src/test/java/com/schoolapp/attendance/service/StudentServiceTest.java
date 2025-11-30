package com.schoolapp.attendance.service;

import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Service Tests")
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "John Doe", "S001", "john@example.com");
    }

    @Test
    @DisplayName("Should add a new student successfully")
    void testAddStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        Student result = studentService.addStudent(testStudent);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("S001", result.getRollNumber());
        verify(studentRepository, times(1)).save(testStudent);
    }

    @Test
    @DisplayName("Should return all students")
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", "S001", "john@example.com"),
                new Student(2L, "Jane Smith", "S002", "jane@example.com")
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void testGetAllStudentsEmpty() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        List<Student> result = studentService.getAllStudents();

        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get student by roll number")
    void testGetStudentByRollNumber() {
        when(studentRepository.findByRollNumber("S001")).thenReturn(testStudent);

        Student result = studentService.getStudentByRollNumber("S001");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("S001", result.getRollNumber());
        verify(studentRepository, times(1)).findByRollNumber("S001");
    }

    @Test
    @DisplayName("Should return null when student roll number not found")
    void testGetStudentByRollNumberNotFound() {
        when(studentRepository.findByRollNumber("S999")).thenReturn(null);

        Student result = studentService.getStudentByRollNumber("S999");

        assertNull(result);
        verify(studentRepository, times(1)).findByRollNumber("S999");
    }

    @Test
    @DisplayName("Should call repository with correct roll number")
    void testGetStudentByRollNumberCallsRepository() {
        studentService.getStudentByRollNumber("S005");

        verify(studentRepository, times(1)).findByRollNumber("S005");
    }

    @Test
    @DisplayName("Should add student with all fields populated")
    void testAddStudentWithAllFields() {
        Student studentWithAllFields = new Student(
                null,
                "Complete Student",
                "S010",
                "complete@example.com"
        );
        when(studentRepository.save(studentWithAllFields)).thenReturn(testStudent);

        Student result = studentService.addStudent(studentWithAllFields);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(studentWithAllFields);
    }

    @Test
    @DisplayName("Should handle multiple students with same email")
    void testMultipleStudentsDifferentEmails() {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", "S001", "john@example.com"),
                new Student(2L, "John Duplicate", "S002", "john2@example.com")
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals("john2@example.com", result.get(1).getEmail());
    }

    @Test
    @DisplayName("Should verify repository interaction for add student")
    void testAddStudentRepositoryInteraction() {
        Student newStudent = new Student(null, "New Student", "S999", "new@example.com");
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        studentService.addStudent(newStudent);

        verify(studentRepository).save(newStudent);
    }
}

