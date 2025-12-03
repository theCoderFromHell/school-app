package com.schoolapp.attendance.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.schoolapp.attendance.model.Teacher;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmployeeId(String employeeId);
    List<Teacher> findBySchoolId(Long schoolId);
    List<Teacher> findByName(String name);
}

