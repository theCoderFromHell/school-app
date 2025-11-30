package com.schoolapp.attendance.service;

import com.schoolapp.attendance.model.Attendance;
import com.schoolapp.attendance.model.Student;
import com.schoolapp.attendance.repository.AttendanceRepository;
import com.schoolapp.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Attendance markAttendance(Long studentId, LocalDate date, Attendance.Status status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if attendance already exists for this student and date
        List<Attendance> existing = attendanceRepository.findByStudentAndDate(student, date);
        if (!existing.isEmpty()) {
            // Update existing attendance
            Attendance attendance = existing.get(0);
            attendance.setStatus(status);
            return attendanceRepository.save(attendance);
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setDate(date);
        attendance.setStatus(status);
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return attendanceRepository.findByStudent(student);
    }
}

