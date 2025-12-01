package com.schoolapp.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.schoolapp.attendance.repository.SchoolClassRepository;
import com.schoolapp.attendance.model.SchoolClass;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public SchoolClass addSchoolClass(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public List<SchoolClass> getAllSchoolClasses() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass getSchoolClassById(Long id) {
        return schoolClassRepository.findById(id).orElse(null);
    }

    public List<SchoolClass> getClassesBySchoolId(Long schoolId) {
        return schoolClassRepository.findBySchoolId(schoolId);
    }

    public SchoolClass getSchoolClassByNameAndSchool(String className, Long schoolId) {
        return schoolClassRepository.findByClassNameAndSchoolId(className, schoolId);
    }

    public SchoolClass updateSchoolClass(Long id, SchoolClass schoolClassDetails) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);
        if (schoolClass != null) {
            schoolClass.setClassName(schoolClassDetails.getClassName());
            schoolClass.setClassTeacher(schoolClassDetails.getClassTeacher());
            schoolClass.setCapacity(schoolClassDetails.getCapacity());
            return schoolClassRepository.save(schoolClass);
        }
        return null;
    }

    public void deleteSchoolClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}

