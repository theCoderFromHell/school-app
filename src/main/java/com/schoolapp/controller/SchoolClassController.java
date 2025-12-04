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
import com.schoolapp.service.SchoolClassService;
import com.schoolapp.model.SchoolClass;

@RestController
@RequestMapping("/api/school-classes")
@CrossOrigin(origins = "*")
@Tag(name = "School Class Management", description = "APIs for managing school classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;

    @Operation(summary = "Add a new school class", description = "Create a new class in a school")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "School class created successfully",
                     content = @Content(schema = @Schema(implementation = SchoolClass.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<SchoolClass> addSchoolClass(@RequestBody SchoolClass schoolClass) {
        SchoolClass savedClass = schoolClassService.addSchoolClass(schoolClass);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all school classes", description = "Retrieve a list of all school classes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of classes")
    @GetMapping
    public ResponseEntity<List<SchoolClass>> getAllSchoolClasses() {
        List<SchoolClass> classes = schoolClassService.getAllSchoolClasses();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @Operation(summary = "Get school class by ID", description = "Retrieve a specific school class by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "School class found",
                     content = @Content(schema = @Schema(implementation = SchoolClass.class))),
        @ApiResponse(responseCode = "404", description = "School class not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getSchoolClassById(
            @Parameter(description = "ID of the school class to retrieve") @PathVariable Long id) {
        SchoolClass schoolClass = schoolClassService.getSchoolClassById(id);
        if (schoolClass != null) {
            return new ResponseEntity<>(schoolClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get classes by school", description = "Retrieve all classes for a specific school")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved classes for the school")
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<SchoolClass>> getClassesBySchoolId(
            @Parameter(description = "ID of the school") @PathVariable Long schoolId) {
        List<SchoolClass> classes = schoolClassService.getClassesBySchoolId(schoolId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @Operation(summary = "Update school class", description = "Update information for an existing school class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "School class updated successfully"),
        @ApiResponse(responseCode = "404", description = "School class not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SchoolClass> updateSchoolClass(
            @Parameter(description = "ID of the school class to update") @PathVariable Long id,
            @RequestBody SchoolClass schoolClassDetails) {
        SchoolClass updatedClass = schoolClassService.updateSchoolClass(id, schoolClassDetails);
        if (updatedClass != null) {
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete school class", description = "Remove a school class from the system")
    @ApiResponse(responseCode = "204", description = "School class deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolClass(
            @Parameter(description = "ID of the school class to delete") @PathVariable Long id) {
        schoolClassService.deleteSchoolClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

