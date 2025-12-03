package com.schoolapp.attendance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.schoolapp.attendance.service.TeacherService;
import com.schoolapp.attendance.model.Teacher;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // CREATE - Add a new teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    // READ - Get all teachers
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // READ - Get teacher by ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);

        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Get teacher by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Teacher> getTeacherByEmployeeId(@PathVariable String employeeId) {
        Teacher teacher = teacherService.getTeacherByEmployeeId(employeeId);

        if (teacher != null) {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Get teachers by school
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<Teacher>> getTeachersBySchool(@PathVariable Long schoolId) {
        List<Teacher> teachers = teacherService.getTeachersBySchool(schoolId);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // READ - Get teachers by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Teacher>> getTeachersByName(@PathVariable String name) {
        List<Teacher> teachers = teacherService.getTeachersByName(name);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // UPDATE - Update teacher information
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacherDetails);

        if (updatedTeacher != null) {
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - Delete a teacher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        boolean deleted = teacherService.deleteTeacher(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Helper endpoint - Check if teacher exists
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> teacherExists(@PathVariable Long id) {
        boolean exists = teacherService.teacherExists(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Helper endpoint - Get total teacher count
    @GetMapping("/count")
    public ResponseEntity<Long> getTeacherCount() {
        long count = teacherService.countTeachers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}

