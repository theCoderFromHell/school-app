package com.schoolapp.attendance.controller;

import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
@DisplayName("Student Controller Tests")
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        testStudent = new Student(1L, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null);
    }

    @Test
    @DisplayName("Should add student via POST request")
    void testAddStudent() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"rollNumber\":\"S001\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.rollNumber").value("S001"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("Should get all students via GET request")
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null),
                new Student(2L, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null)
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void testGetAllStudentsEmpty() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/students")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should add student with minimal fields")
    void testAddStudentMinimalFields() throws Exception {
        Student minimalStudent = new Student(1L, "Test", "S999", null, null, null, null, null);
        when(studentService.addStudent(any(Student.class))).thenReturn(minimalStudent);

        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content("{\"name\":\"Test\",\"rollNumber\":\"S999\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.rollNumber").value("S999"));
    }

    @Test
    @DisplayName("Should verify correct endpoint mapping for add student")
    void testAddStudentEndpoint() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"rollNumber\":\"S001\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should verify correct endpoint mapping for get all students")
    void testGetAllStudentsEndpoint() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(testStudent));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should handle multiple students with different emails")
    void testGetAllStudentsMultiple() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", "S001", "john@example.com", null, "1234567890", "123 Main St", null),
                new Student(2L, "Jane Smith", "S002", "jane@example.com", null, "9876543210", "456 Oak St", null),
                new Student(3L, "Bob Johnson", "S003", "bob@example.com", null, "5555555555", "789 Elm St", null)
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rollNumber").value("S001"))
                .andExpect(jsonPath("$[1].rollNumber").value("S002"))
                .andExpect(jsonPath("$[2].rollNumber").value("S003"));
    }

    @Test
    @DisplayName("Should return student with all fields")
    void testAddStudentReturnAllFields() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"rollNumber\":\"S001\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.rollNumber").value("S001"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}

