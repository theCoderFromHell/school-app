package com.schoolapp.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.schoolapp.service.StudentService;
import com.schoolapp.model.Student;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing students in the school system")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Get all students", description = "Retrieve a list of all students in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of students")
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Add a new student", description = "Register a new student in the school system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student added successfully",
                     content = @Content(schema = @Schema(implementation = Student.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
