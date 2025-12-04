package com.schoolapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.schoolapp.repository.TeacherRepository;
import com.schoolapp.model.Teacher;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // CREATE
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // READ - Get all teachers
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // READ - Get teacher by ID
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // READ - Get teacher by employee ID
    public Teacher getTeacherByEmployeeId(String employeeId) {
        return teacherRepository.findByEmployeeId(employeeId);
    }

    // READ - Get teachers by school
    public List<Teacher> getTeachersBySchool(Long schoolId) {
        return teacherRepository.findBySchoolId(schoolId);
    }

    // READ - Get teachers by name
    public List<Teacher> getTeachersByName(String name) {
        return teacherRepository.findByName(name);
    }

    // UPDATE
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(id);

        if (existingTeacher.isPresent()) {
            Teacher teacher = existingTeacher.get();

            if (teacherDetails.getName() != null) {
                teacher.setName(teacherDetails.getName());
            }
            if (teacherDetails.getEmail() != null) {
                teacher.setEmail(teacherDetails.getEmail());
            }
            if (teacherDetails.getPhoneNumber() != null) {
                teacher.setPhoneNumber(teacherDetails.getPhoneNumber());
            }
            if (teacherDetails.getAddress() != null) {
                teacher.setAddress(teacherDetails.getAddress());
            }
            if (teacherDetails.getQualification() != null) {
                teacher.setQualification(teacherDetails.getQualification());
            }
            if (teacherDetails.getSpecialization() != null) {
                teacher.setSpecialization(teacherDetails.getSpecialization());
            }
            if (teacherDetails.getSchool() != null) {
                teacher.setSchool(teacherDetails.getSchool());
            }

            return teacherRepository.save(teacher);
        }

        return null;
    }

    // DELETE
    public boolean deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Check if teacher exists
    public boolean teacherExists(Long id) {
        return teacherRepository.existsById(id);
    }

    // Count teachers
    public long countTeachers() {
        return teacherRepository.count();
    }
}
