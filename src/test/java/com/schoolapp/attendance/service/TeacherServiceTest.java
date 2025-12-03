package com.schoolapp.attendance.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import com.schoolapp.attendance.model.Teacher;
import com.schoolapp.attendance.model.School;
import com.schoolapp.attendance.repository.TeacherRepository;
import com.schoolapp.attendance.repository.SchoolRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

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
        school.setName(name + "_" + System.currentTimeMillis());
        school.setAddress("456 School Ave");
        school.setPhoneNumber("0123456789");
        school.setEmail("school@edu.com");
        school.setPrincipal("Dr. Principal");
        return schoolRepository.save(school);
    }

    @Test
    public void testCreateTeacher() {
        School school = createSampleSchool("Test School");
        Teacher teacher = createSampleTeacher("Service Test Teacher", "ST001", school);

        Teacher createdTeacher = teacherService.createTeacher(teacher);

        assertNotNull(createdTeacher.getId());
        assertEquals("Service Test Teacher", createdTeacher.getName());
        assertEquals("ST001", createdTeacher.getEmployeeId());
    }

    @Test
    public void testGetAllTeachers() {
        School school = createSampleSchool("Test School 2");
        Teacher teacher1 = createSampleTeacher("Teacher One", "ST002", school);
        Teacher teacher2 = createSampleTeacher("Teacher Two", "ST003", school);

        teacherService.createTeacher(teacher1);
        teacherService.createTeacher(teacher2);

        List<Teacher> teachers = teacherService.getAllTeachers();

        assertTrue(teachers.size() >= 2);
    }

    @Test
    public void testGetTeacherById() {
        School school = createSampleSchool("Test School 3");
        Teacher teacher = createSampleTeacher("Teacher Three", "ST004", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        Optional<Teacher> foundTeacher = teacherService.getTeacherById(savedTeacher.getId());

        assertTrue(foundTeacher.isPresent());
        assertEquals("Teacher Three", foundTeacher.get().getName());
    }

    @Test
    public void testGetTeacherByEmployeeId() {
        School school = createSampleSchool("Test School 4");
        Teacher teacher = createSampleTeacher("Teacher Four", "ST005", school);
        teacherService.createTeacher(teacher);

        Teacher foundTeacher = teacherService.getTeacherByEmployeeId("ST005");

        assertNotNull(foundTeacher);
        assertEquals("Teacher Four", foundTeacher.getName());
    }

    @Test
    public void testGetTeachersBySchool() {
        School school = createSampleSchool("Test School 5");
        Teacher teacher1 = createSampleTeacher("Teacher Five", "ST006", school);
        Teacher teacher2 = createSampleTeacher("Teacher Six", "ST007", school);

        teacherService.createTeacher(teacher1);
        teacherService.createTeacher(teacher2);

        List<Teacher> teachers = teacherService.getTeachersBySchool(school.getId());

        assertEquals(2, teachers.size());
    }

    @Test
    public void testGetTeachersByName() {
        School school = createSampleSchool("Test School 6");
        Teacher teacher1 = createSampleTeacher("John Smith", "ST008", school);
        Teacher teacher2 = createSampleTeacher("Jane Doe", "ST009", school);

        teacherService.createTeacher(teacher1);
        teacherService.createTeacher(teacher2);

        List<Teacher> teachers = teacherService.getTeachersByName("John Smith");

        assertEquals(1, teachers.size());
        assertEquals("John Smith", teachers.get(0).getName());
    }

    @Test
    public void testUpdateTeacher() {
        School school = createSampleSchool("Test School 7");
        Teacher teacher = createSampleTeacher("Teacher Seven", "ST010", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        Teacher updateData = new Teacher();
        updateData.setEmail("newemail@school.com");
        updateData.setPhoneNumber("1234567890");
        updateData.setQualification("M.Ed");

        Teacher updatedTeacher = teacherService.updateTeacher(savedTeacher.getId(), updateData);

        assertNotNull(updatedTeacher);
        assertEquals("newemail@school.com", updatedTeacher.getEmail());
        assertEquals("1234567890", updatedTeacher.getPhoneNumber());
        assertEquals("M.Ed", updatedTeacher.getQualification());
    }

    @Test
    public void testUpdateNonExistentTeacher() {
        Teacher updateData = new Teacher();
        updateData.setName("Test");

        Teacher result = teacherService.updateTeacher(99999L, updateData);

        assertNull(result);
    }

    @Test
    public void testDeleteTeacher() {
        School school = createSampleSchool("Test School 8");
        Teacher teacher = createSampleTeacher("Teacher Eight", "ST011", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        boolean deleted = teacherService.deleteTeacher(savedTeacher.getId());

        assertTrue(deleted);
        assertFalse(teacherService.teacherExists(savedTeacher.getId()));
    }

    @Test
    public void testDeleteNonExistentTeacher() {
        boolean deleted = teacherService.deleteTeacher(99999L);

        assertFalse(deleted);
    }

    @Test
    public void testTeacherExists() {
        School school = createSampleSchool("Test School 9");
        Teacher teacher = createSampleTeacher("Teacher Nine", "ST012", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        assertTrue(teacherService.teacherExists(savedTeacher.getId()));
        assertFalse(teacherService.teacherExists(99999L));
    }

    @Test
    public void testCountTeachers() {
        School school = createSampleSchool("Test School 10");
        Teacher teacher1 = createSampleTeacher("Teacher Ten", "ST013", school);
        Teacher teacher2 = createSampleTeacher("Teacher Eleven", "ST014", school);

        long initialCount = teacherService.countTeachers();

        teacherService.createTeacher(teacher1);
        teacherService.createTeacher(teacher2);

        long finalCount = teacherService.countTeachers();

        assertEquals(initialCount + 2, finalCount);
    }

    @Test
    public void testTeacherWithCompleteDetails() {
        School school = createSampleSchool("Test School 11");
        Teacher teacher = createSampleTeacher("Teacher Complete", "ST015", school);
        teacher.setQualification("B.Tech");
        teacher.setSpecialization("Physics");

        Teacher createdTeacher = teacherService.createTeacher(teacher);
        Optional<Teacher> foundTeacher = teacherService.getTeacherById(createdTeacher.getId());

        assertTrue(foundTeacher.isPresent());
        assertEquals("B.Tech", foundTeacher.get().getQualification());
        assertEquals("Physics", foundTeacher.get().getSpecialization());
    }

    @Test
    public void testUpdateMultipleTeacherFields() {
        School school = createSampleSchool("Test School 12");
        Teacher teacher = createSampleTeacher("Teacher Multi", "ST016", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        Teacher updateData = new Teacher();
        updateData.setName("Teacher Multi Updated");
        updateData.setEmail("updated@school.com");
        updateData.setPhoneNumber("9999999999");
        updateData.setAddress("New Address");
        updateData.setQualification("Ph.D");
        updateData.setSpecialization("Advanced Mathematics");

        Teacher updatedTeacher = teacherService.updateTeacher(savedTeacher.getId(), updateData);

        assertNotNull(updatedTeacher);
        assertEquals("Teacher Multi Updated", updatedTeacher.getName());
        assertEquals("updated@school.com", updatedTeacher.getEmail());
        assertEquals("9999999999", updatedTeacher.getPhoneNumber());
        assertEquals("New Address", updatedTeacher.getAddress());
        assertEquals("Ph.D", updatedTeacher.getQualification());
        assertEquals("Advanced Mathematics", updatedTeacher.getSpecialization());
    }

    @Test
    public void testPartialTeacherUpdate() {
        School school = createSampleSchool("Test School 13");
        Teacher teacher = createSampleTeacher("Teacher Partial", "ST017", school);
        Teacher savedTeacher = teacherService.createTeacher(teacher);

        String originalName = savedTeacher.getName();

        Teacher updateData = new Teacher();
        updateData.setEmail("partial@school.com");
        // Only updating email, not name

        Teacher updatedTeacher = teacherService.updateTeacher(savedTeacher.getId(), updateData);

        assertNotNull(updatedTeacher);
        assertEquals(originalName, updatedTeacher.getName());
        assertEquals("partial@school.com", updatedTeacher.getEmail());
    }

    @Test
    public void testTeacherServiceIntegration() {
        School school = createSampleSchool("Test School 14");

        // Create
        Teacher teacher1 = createSampleTeacher("Integration Teacher 1", "INT001", school);
        Teacher teacher2 = createSampleTeacher("Integration Teacher 2", "INT002", school);

        Teacher created1 = teacherService.createTeacher(teacher1);
        Teacher created2 = teacherService.createTeacher(teacher2);

        // Read
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        assertTrue(allTeachers.size() >= 2);

        // Update
        Teacher updateData = new Teacher();
        updateData.setQualification("M.Tech");
        Teacher updated = teacherService.updateTeacher(created1.getId(), updateData);
        assertEquals("M.Tech", updated.getQualification());

        // Delete
        teacherService.deleteTeacher(created2.getId());
        assertFalse(teacherService.teacherExists(created2.getId()));
    }
}

