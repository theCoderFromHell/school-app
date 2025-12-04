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
import com.schoolapp.service.SchoolService;
import com.schoolapp.model.School;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "*")
@Tag(name = "School Management", description = "APIs for managing schools in the system")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Operation(summary = "Add a new school", description = "Register a new school in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "School created successfully",
                     content = @Content(schema = @Schema(implementation = School.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<School> addSchool(@RequestBody School school) {
        School savedSchool = schoolService.addSchool(school);
        return new ResponseEntity<>(savedSchool, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all schools", description = "Retrieve a list of all schools")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of schools")
    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.getAllSchools();
        return new ResponseEntity<>(schools, HttpStatus.OK);
    }

    @Operation(summary = "Get school by ID", description = "Retrieve a specific school by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "School found",
                     content = @Content(schema = @Schema(implementation = School.class))),
        @ApiResponse(responseCode = "404", description = "School not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(
            @Parameter(description = "ID of the school to retrieve") @PathVariable Long id) {
        School school = schoolService.getSchoolById(id);
        if (school != null) {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get school by name", description = "Retrieve a school by its name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "School found"),
        @ApiResponse(responseCode = "404", description = "School not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<School> getSchoolByName(
            @Parameter(description = "Name of the school") @PathVariable String name) {
        School school = schoolService.getSchoolByName(name);
        if (school != null) {
            return new ResponseEntity<>(school, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update school", description = "Update information for an existing school")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "School updated successfully"),
        @ApiResponse(responseCode = "404", description = "School not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(
            @Parameter(description = "ID of the school to update") @PathVariable Long id,
            @RequestBody School schoolDetails) {
        School updatedSchool = schoolService.updateSchool(id, schoolDetails);
        if (updatedSchool != null) {
            return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete school", description = "Remove a school from the system")
    @ApiResponse(responseCode = "204", description = "School deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(
            @Parameter(description = "ID of the school to delete") @PathVariable Long id) {
        schoolService.deleteSchool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

