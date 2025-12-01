package com.schoolapp.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.schoolapp.attendance.service.SchoolClassService;
import com.schoolapp.attendance.model.SchoolClass;

@RestController
@RequestMapping("/api/school-classes")
@CrossOrigin(origins = "*")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @PostMapping
    public ResponseEntity<SchoolClass> addSchoolClass(@RequestBody SchoolClass schoolClass) {
        SchoolClass savedClass = schoolClassService.addSchoolClass(schoolClass);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchoolClass>> getAllSchoolClasses() {
        List<SchoolClass> classes = schoolClassService.getAllSchoolClasses();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getSchoolClassById(@PathVariable Long id) {
        SchoolClass schoolClass = schoolClassService.getSchoolClassById(id);
        if (schoolClass != null) {
            return new ResponseEntity<>(schoolClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<SchoolClass>> getClassesBySchoolId(@PathVariable Long schoolId) {
        List<SchoolClass> classes = schoolClassService.getClassesBySchoolId(schoolId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolClass> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClass schoolClassDetails) {
        SchoolClass updatedClass = schoolClassService.updateSchoolClass(id, schoolClassDetails);
        if (updatedClass != null) {
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable Long id) {
        schoolClassService.deleteSchoolClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

