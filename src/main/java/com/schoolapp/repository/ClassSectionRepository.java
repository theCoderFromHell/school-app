package com.schoolapp.repository;

import com.schoolapp.model.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
    List<ClassSection> findBySchoolClassId(Long schoolClassId);
    ClassSection findBySectionNameAndSchoolClassId(String sectionName, Long schoolClassId);
}

