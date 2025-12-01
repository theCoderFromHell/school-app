package com.schoolapp.attendance.repository;

import com.schoolapp.attendance.model.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
    List<ClassSection> findBySchoolClassId(Long schoolClassId);
    ClassSection findBySectionNameAndSchoolClassId(String sectionName, Long schoolClassId);
}

