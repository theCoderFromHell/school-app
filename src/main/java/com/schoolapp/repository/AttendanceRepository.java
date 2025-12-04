package com.schoolapp.repository;

import com.schoolapp.model.Attendance;
import com.schoolapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(Student student);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByStudentAndDate(Student student, LocalDate date);
}

