package com.schoolapp.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.schoolapp.service.TeacherService;
import com.schoolapp.model.Teacher;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Management", description = "APIs for managing teachers in the school system")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // CREATE - Add a new teacher
    @Operation(summary = "Create a new teacher", description = "Add a new teacher to the school system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Teacher created successfully", 
                     content = @Content(schema = @Schema(implementation = Teacher.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    // READ - Get all teachers
    @Operation(summary = "Get all teachers", description = "Retrieve a list of all teachers in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of teachers")
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // READ - Get teacher by ID
    @Operation(summary = "Get teacher by ID", description = "Retrieve a specific teacher by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher found", 
                     content = @Content(schema = @Schema(implementation = Teacher.class))),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(
            @Parameter(description = "ID of the teacher to retrieve") @PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);

        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Get teacher by employee ID
    @Operation(summary = "Get teacher by employee ID", description = "Retrieve a teacher by their employee ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher found"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Teacher> getTeacherByEmployeeId(
            @Parameter(description = "Employee ID of the teacher") @PathVariable String employeeId) {
        Teacher teacher = teacherService.getTeacherByEmployeeId(employeeId);

        if (teacher != null) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Get teachers by school
    @Operation(summary = "Get teachers by school", description = "Retrieve all teachers for a specific school")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved teachers for the school")
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<Teacher>> getTeachersBySchool(
            @Parameter(description = "ID of the school") @PathVariable Long schoolId) {
        List<Teacher> teachers = teacherService.getTeachersBySchool(schoolId);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // READ - Get teachers by name
    @Operation(summary = "Get teachers by name", description = "Search for teachers by their name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved teachers matching the name")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Teacher>> getTeachersByName(
            @Parameter(description = "Name to search for") @PathVariable String name) {
        List<Teacher> teachers = teacherService.getTeachersByName(name);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // UPDATE - Update teacher information
    @Operation(summary = "Update teacher", description = "Update information for an existing teacher")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher updated successfully"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(
            @Parameter(description = "ID of the teacher to update") @PathVariable Long id, 
            @RequestBody Teacher teacherDetails) {
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacherDetails);

        if (updatedTeacher != null) {
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - Delete a teacher
    @Operation(summary = "Delete teacher", description = "Remove a teacher from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Teacher deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @Parameter(description = "ID of the teacher to delete") @PathVariable Long id) {
        boolean deleted = teacherService.deleteTeacher(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Helper endpoint - Check if teacher exists
    @Operation(summary = "Check if teacher exists", description = "Verify if a teacher exists by their ID")
    @ApiResponse(responseCode = "200", description = "Returns true if teacher exists, false otherwise")
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> teacherExists(
            @Parameter(description = "ID of the teacher to check") @PathVariable Long id) {
        boolean exists = teacherService.teacherExists(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Helper endpoint - Get total teacher count
    @Operation(summary = "Get teacher count", description = "Get the total number of teachers in the system")
    @ApiResponse(responseCode = "200", description = "Returns the total count of teachers")
    @GetMapping("/count")
    public ResponseEntity<Long> getTeacherCount() {
        long count = teacherService.countTeachers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}

