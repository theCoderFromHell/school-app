package com.schoolapp.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.schoolapp.attendance.service.SchoolService;
import com.schoolapp.attendance.model.School;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "*")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public ResponseEntity<School> addSchool(@RequestBody School school) {
        School savedSchool = schoolService.addSchool(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.getAllSchools();
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        School school = schoolService.getSchoolById(id);
        if (school != null) {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<School> getSchoolByName(@PathVariable String name) {
        School school = schoolService.getSchoolByName(name);
        if (school != null) {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody School schoolDetails) {
        School updatedSchool = schoolService.updateSchool(id, schoolDetails);
        if (updatedSchool != null) {
            return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

