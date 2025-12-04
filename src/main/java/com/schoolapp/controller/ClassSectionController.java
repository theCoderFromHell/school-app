package com.schoolapp.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.schoolapp.service.ClassSectionService;
import com.schoolapp.model.ClassSection;

@RestController
@RequestMapping("/api/class-sections")
@CrossOrigin(origins = "*")
@Tag(name = "Class Section Management", description = "APIs for managing class sections")
public class ClassSectionController {

    @Autowired
    private ClassSectionService classSectionService;

    @Operation(summary = "Add a new class section", description = "Create a new section within a school class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Class section created successfully",
                     content = @Content(schema = @Schema(implementation = ClassSection.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ClassSection> addClassSection(@RequestBody ClassSection classSection) {
        ClassSection savedSection = classSectionService.addClassSection(classSection);
        return new ResponseEntity<>(savedSection, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all class sections", description = "Retrieve a list of all class sections")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sections")
    @GetMapping
    public ResponseEntity<List<ClassSection>> getAllClassSections() {
        List<ClassSection> sections = classSectionService.getAllClassSections();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @Operation(summary = "Get class section by ID", description = "Retrieve a specific class section by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Class section found",
                     content = @Content(schema = @Schema(implementation = ClassSection.class))),
        @ApiResponse(responseCode = "404", description = "Class section not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClassSection> getClassSectionById(
            @Parameter(description = "ID of the class section to retrieve") @PathVariable Long id) {
        ClassSection classSection = classSectionService.getClassSectionById(id);
        if (classSection != null) {
            return new ResponseEntity<>(classSection, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get sections by school class", description = "Retrieve all sections for a specific school class")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved sections for the school class")
    @GetMapping("/school-class/{schoolClassId}")
    public ResponseEntity<List<ClassSection>> getSectionsBySchoolClassId(
            @Parameter(description = "ID of the school class") @PathVariable Long schoolClassId) {
        List<ClassSection> sections = classSectionService.getSectionsBySchoolClassId(schoolClassId);
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @Operation(summary = "Update class section", description = "Update information for an existing class section")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Class section updated successfully"),
        @ApiResponse(responseCode = "404", description = "Class section not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClassSection> updateClassSection(
            @Parameter(description = "ID of the class section to update") @PathVariable Long id,
            @RequestBody ClassSection classSectionDetails) {
        ClassSection updatedSection = classSectionService.updateClassSection(id, classSectionDetails);
        if (updatedSection != null) {
            return new ResponseEntity<>(updatedSection, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete class section", description = "Remove a class section from the system")
    @ApiResponse(responseCode = "204", description = "Class section deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSection(
            @Parameter(description = "ID of the class section to delete") @PathVariable Long id) {
        classSectionService.deleteClassSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

