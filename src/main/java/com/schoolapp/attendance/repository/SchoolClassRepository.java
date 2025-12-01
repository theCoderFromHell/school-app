package com.schoolapp.attendance.repository;

import com.schoolapp.attendance.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findBySchoolId(Long schoolId);
    SchoolClass findByClassNameAndSchoolId(String className, Long schoolId);
}

