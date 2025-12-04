package com.schoolapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.schoolapp.repository.SchoolRepository;
import com.schoolapp.model.School;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public School addSchool(School school) {
        return schoolRepository.save(school);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public School getSchoolById(Long id) {
        return schoolRepository.findById(id).orElse(null);
    }

    public School getSchoolByName(String name) {
        return schoolRepository.findByName(name);
    }

    public School updateSchool(Long id, School schoolDetails) {
        School school = schoolRepository.findById(id).orElse(null);
        if (school != null) {
            school.setName(schoolDetails.getName());
            school.setAddress(schoolDetails.getAddress());
            school.setPhoneNumber(schoolDetails.getPhoneNumber());
            school.setEmail(schoolDetails.getEmail());
            school.setPrincipal(schoolDetails.getPrincipal());
            return schoolRepository.save(school);
        }
        return null;
    }

    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }
}
