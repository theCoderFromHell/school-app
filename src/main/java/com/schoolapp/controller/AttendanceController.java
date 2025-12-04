package com.schoolapp.controller;

import com.schoolapp.model.Attendance;
import com.schoolapp.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance Management", description = "APIs for tracking and managing student attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Operation(summary = "Mark attendance", description = "Record attendance for a student on a specific date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance marked successfully",
                     content = @Content(schema = @Schema(implementation = Attendance.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Attendance markAttendance(@RequestBody Map<String, Object> payload) {
        Long studentId = Long.valueOf(payload.get("studentId").toString());
        LocalDate date = LocalDate.parse(payload.get("date").toString());
        Attendance.Status status = Attendance.Status.valueOf(payload.get("status").toString());
        return attendanceService.markAttendance(studentId, date, status);
    }

    @Operation(summary = "Get attendance by date", description = "Retrieve all attendance records for a specific date")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved attendance records")
    @GetMapping("/date/{date}")
    public List<Attendance> getAttendanceByDate(
            @Parameter(description = "Date in YYYY-MM-DD format") @PathVariable String date) {
        return attendanceService.getAttendanceByDate(LocalDate.parse(date));
    }

    @Operation(summary = "Get attendance by student", description = "Retrieve all attendance records for a specific student")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved student attendance records")
    @GetMapping("/student/{studentId}")
    public List<Attendance> getAttendanceByStudent(
            @Parameter(description = "ID of the student") @PathVariable Long studentId) {
        return attendanceService.getAttendanceByStudent(studentId);
    }
}

