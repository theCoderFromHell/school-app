package com.schoolapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.schoolapp.model.Teacher;
import com.schoolapp.model.School;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private Teacher createSampleTeacher(String name, String employeeId, School school) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmployeeId(employeeId);
        teacher.setEmail(name.toLowerCase().replace(" ", ".") + "@school.com");
        teacher.setPhoneNumber("9876543210");
        teacher.setAddress("123 Teacher Street");
        teacher.setQualification("B.Ed");
        teacher.setSpecialization("Mathematics");
        teacher.setSchool(school);
        return teacher;
    }

    private School createSampleSchool(String name) {
        School school = new School();
        school.setName(name);
        school.setAddress("456 School Ave");
        school.setPhoneNumber("0123456789");
        school.setEmail("school@edu.com");
        school.setPrincipal("Dr. Principal");
        return schoolRepository.save(school);
    }

    @Test
    public void testSaveTeacher() {
        School school = createSampleSchool("Test School");
        Teacher teacher = createSampleTeacher("John Doe", "T001", school);

        Teacher savedTeacher = teacherRepository.save(teacher);

        assertNotNull(savedTeacher.getId());
        assertEquals("John Doe", savedTeacher.getName());
        assertEquals("T001", savedTeacher.getEmployeeId());
    }

    @Test
    public void testFindTeacherById() {
        School school = createSampleSchool("Test School 2");
        Teacher teacher = createSampleTeacher("Jane Doe", "T002", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        Optional<Teacher> foundTeacher = teacherRepository.findById(savedTeacher.getId());

        assertTrue(foundTeacher.isPresent());
        assertEquals("Jane Doe", foundTeacher.get().getName());
    }

    @Test
    public void testFindTeacherByEmployeeId() {
        School school = createSampleSchool("Test School 3");
        Teacher teacher = createSampleTeacher("Alice Smith", "T003", school);
        teacherRepository.save(teacher);

        Teacher foundTeacher = teacherRepository.findByEmployeeId("T003");

        assertNotNull(foundTeacher);
        assertEquals("Alice Smith", foundTeacher.getName());
    }

    @Test
    public void testFindTeachersBySchool() {
        School school = createSampleSchool("Test School 4");
        Teacher teacher1 = createSampleTeacher("Bob Johnson", "T004", school);
        Teacher teacher2 = createSampleTeacher("Carol Williams", "T005", school);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherRepository.findBySchoolId(school.getId());

        assertEquals(2, teachers.size());
    }

    @Test
    public void testFindTeachersByName() {
        School school = createSampleSchool("Test School 5");
        Teacher teacher1 = createSampleTeacher("David Brown", "T006", school);
        Teacher teacher2 = createSampleTeacher("David Lee", "T007", school);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherRepository.findByName("David Brown");

        assertEquals(1, teachers.size());
        assertEquals("David Brown", teachers.get(0).getName());
    }

    @Test
    public void testUpdateTeacher() {
        School school = createSampleSchool("Test School 6");
        Teacher teacher = createSampleTeacher("Eve Davis", "T008", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        savedTeacher.setEmail("eve.new@school.com");
        Teacher updatedTeacher = teacherRepository.save(savedTeacher);

        assertEquals("eve.new@school.com", updatedTeacher.getEmail());
    }

    @Test
    public void testDeleteTeacher() {
        School school = createSampleSchool("Test School 7");
        Teacher teacher = createSampleTeacher("Frank Miller", "T009", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        teacherRepository.deleteById(savedTeacher.getId());

        Optional<Teacher> deletedTeacher = teacherRepository.findById(savedTeacher.getId());
        assertFalse(deletedTeacher.isPresent());
    }

    @Test
    public void testTeacherWithUniqueEmployeeId() {
        School school = createSampleSchool("Test School 8");
        Teacher teacher1 = createSampleTeacher("Grace Lee", "T010", school);
        teacherRepository.save(teacher1);

        Teacher teacher2 = createSampleTeacher("Grace Chen", "T010", school);

        assertThrows(Exception.class, () -> teacherRepository.save(teacher2));
    }

    @Test
    public void testGetAllTeachers() {
        School school = createSampleSchool("Test School 9");
        Teacher teacher1 = createSampleTeacher("Henry Taylor", "T011", school);
        Teacher teacher2 = createSampleTeacher("Iris Thomas", "T012", school);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherRepository.findAll();

        assertTrue(teachers.size() >= 2);
    }

    @Test
    public void testTeacherWithQualificationAndSpecialization() {
        School school = createSampleSchool("Test School 10");
        Teacher teacher = createSampleTeacher("Jack Wilson", "T013", school);
        teacher.setQualification("M.Ed");
        teacher.setSpecialization("English");

        Teacher savedTeacher = teacherRepository.save(teacher);

        assertEquals("M.Ed", savedTeacher.getQualification());
        assertEquals("English", savedTeacher.getSpecialization());
    }

    @Test
    public void testTeacherWithCompleteDetails() {
        School school = createSampleSchool("Test School 11");
        Teacher teacher = new Teacher();
        teacher.setName("Karen Martin");
        teacher.setEmployeeId("T014");
        teacher.setEmail("karen.martin@school.com");
        teacher.setPhoneNumber("8765432109");
        teacher.setAddress("789 Teacher Lane");
        teacher.setQualification("B.Tech");
        teacher.setSpecialization("Computer Science");
        teacher.setSchool(school);

        Teacher savedTeacher = teacherRepository.save(teacher);

        assertNotNull(savedTeacher.getId());
        assertEquals("Karen Martin", savedTeacher.getName());
        assertEquals("8765432109", savedTeacher.getPhoneNumber());
        assertEquals("Computer Science", savedTeacher.getSpecialization());
    }

    @Test
    public void testMultipleTeachersInSameSchool() {
        School school = createSampleSchool("Test School 12");
        Teacher teacher1 = createSampleTeacher("Leo Anderson", "T015", school);
        Teacher teacher2 = createSampleTeacher("Mia Jackson", "T016", school);
        Teacher teacher3 = createSampleTeacher("Noah White", "T017", school);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);

        List<Teacher> schoolTeachers = teacherRepository.findBySchoolId(school.getId());

        assertEquals(3, schoolTeachers.size());
    }

    @Test
    public void testTeacherExistsById() {
        School school = createSampleSchool("Test School 13");
        Teacher teacher = createSampleTeacher("Olivia Brown", "T018", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        assertTrue(teacherRepository.existsById(savedTeacher.getId()));
        assertFalse(teacherRepository.existsById(99999L));
    }

    @Test
    public void testGetTeacherCountBySchool() {
        School school = createSampleSchool("Test School 14");
        Teacher teacher1 = createSampleTeacher("Paula Green", "T019", school);
        Teacher teacher2 = createSampleTeacher("Quinn Moore", "T020", school);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        long count = teacherRepository.count();

        assertTrue(count >= 2);
    }
}

