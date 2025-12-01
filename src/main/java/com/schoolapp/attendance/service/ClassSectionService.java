package com.schoolapp.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.schoolapp.attendance.repository.ClassSectionRepository;
import com.schoolapp.attendance.model.ClassSection;

@Service
public class ClassSectionService {

    @Autowired
    private ClassSectionRepository classSectionRepository;

    public ClassSection addClassSection(ClassSection classSection) {
        return classSectionRepository.save(classSection);
    }

    public List<ClassSection> getAllClassSections() {
        return classSectionRepository.findAll();
    }

    public ClassSection getClassSectionById(Long id) {
        return classSectionRepository.findById(id).orElse(null);
    }

    public List<ClassSection> getSectionsBySchoolClassId(Long schoolClassId) {
        return classSectionRepository.findBySchoolClassId(schoolClassId);
    }

    public ClassSection getClassSectionByNameAndClass(String sectionName, Long schoolClassId) {
        return classSectionRepository.findBySectionNameAndSchoolClassId(sectionName, schoolClassId);
    }

    public ClassSection updateClassSection(Long id, ClassSection classSectionDetails) {
        ClassSection classSection = classSectionRepository.findById(id).orElse(null);
        if (classSection != null) {
            classSection.setSectionName(classSectionDetails.getSectionName());
            classSection.setSectionTeacher(classSectionDetails.getSectionTeacher());
            classSection.setStrength(classSectionDetails.getStrength());
            return classSectionRepository.save(classSection);
        }
        return null;
    }

    public void deleteClassSection(Long id) {
        classSectionRepository.deleteById(id);
    }
}

