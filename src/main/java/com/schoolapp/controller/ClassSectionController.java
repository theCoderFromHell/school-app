package com.schoolapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.schoolapp.service.ClassSectionService;
import com.schoolapp.model.ClassSection;

@RestController
@RequestMapping("/api/class-sections")
@CrossOrigin(origins = "*")
public class ClassSectionController {

    @Autowired
    private ClassSectionService classSectionService;

    @PostMapping
    public ResponseEntity<ClassSection> addClassSection(@RequestBody ClassSection classSection) {
        ClassSection savedSection = classSectionService.addClassSection(classSection);
        return new ResponseEntity<>(savedSection, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClassSection>> getAllClassSections() {
        List<ClassSection> sections = classSectionService.getAllClassSections();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSection> getClassSectionById(@PathVariable Long id) {
        ClassSection classSection = classSectionService.getClassSectionById(id);
        if (classSection != null) {
            return new ResponseEntity<>(classSection, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/school-class/{schoolClassId}")
    public ResponseEntity<List<ClassSection>> getSectionsBySchoolClassId(@PathVariable Long schoolClassId) {
        List<ClassSection> sections = classSectionService.getSectionsBySchoolClassId(schoolClassId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSection> updateClassSection(@PathVariable Long id, @RequestBody ClassSection classSectionDetails) {
        ClassSection updatedSection = classSectionService.updateClassSection(id, classSectionDetails);
        if (updatedSection != null) {
            return new ResponseEntity<>(updatedSection, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSection(@PathVariable Long id) {
        classSectionService.deleteClassSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

