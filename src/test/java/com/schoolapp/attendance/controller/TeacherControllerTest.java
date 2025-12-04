package com.schoolapp.attendance.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolapp.attendance.model.Teacher;
import com.schoolapp.attendance.model.School;
import com.schoolapp.attendance.repository.TeacherRepository;
import com.schoolapp.attendance.repository.SchoolRepository;
import com.schoolapp.attendance.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testCreateTeacher() throws Exception {
        School school = createSampleSchool("Test School");
        Teacher teacher = createSampleTeacher("Controller Test", "CT001", school);

        mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Controller Test")))
                .andExpect(jsonPath("$.employeeId", equalTo("CT001")));
    }

    @Test
    public void testGetAllTeachers() throws Exception {
        School school = createSampleSchool("Test School 2");
        Teacher teacher1 = createSampleTeacher("Teacher A", "TA001", school);
        Teacher teacher2 = createSampleTeacher("Teacher B", "TA002", school);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        mockMvc.perform(get("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    public void testGetTeacherById() throws Exception {
        School school = createSampleSchool("Test School 3");
        Teacher teacher = createSampleTeacher("Teacher C", "TC001", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        mockMvc.perform(get("/api/teachers/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Teacher C")))
                .andExpect(jsonPath("$.employeeId", equalTo("TC001")));
    }

    @Test
    public void testGetTeacherByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/teachers/{id}", 99999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTeacherByEmployeeId() throws Exception {
        School school = createSampleSchool("Test School 4");
        Teacher teacher = createSampleTeacher("Teacher D", "TD001", school);
        teacherRepository.save(teacher);

        mockMvc.perform(get("/api/teachers/employee/{employeeId}", "TD001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Teacher D")));
    }

    @Test
    public void testGetTeachersBySchool() throws Exception {
        School school = createSampleSchool("Test School 5");
        Teacher teacher1 = createSampleTeacher("Teacher E", "TE001", school);
        Teacher teacher2 = createSampleTeacher("Teacher F", "TE002", school);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        mockMvc.perform(get("/api/teachers/school/{schoolId}", school.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    public void testGetTeachersByName() throws Exception {
        School school = createSampleSchool("Test School 6");
        Teacher teacher = createSampleTeacher("Teacher G", "TG001", school);
        teacherRepository.save(teacher);

        mockMvc.perform(get("/api/teachers/name/{name}", "Teacher G")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Teacher G")));
    }

    @Test
    public void testUpdateTeacher() throws Exception {
        School school = createSampleSchool("Test School 7");
        Teacher teacher = createSampleTeacher("Teacher H", "TH001", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        Teacher updateData = new Teacher();
        updateData.setEmail("newemail@school.com");
        updateData.setPhoneNumber("9999999999");

        mockMvc.perform(put("/api/teachers/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("newemail@school.com")))
                .andExpect(jsonPath("$.phoneNumber", equalTo("9999999999")));
    }

    @Test
    public void testUpdateNonExistentTeacher() throws Exception {
        Teacher updateData = new Teacher();
        updateData.setEmail("test@school.com");

        mockMvc.perform(put("/api/teachers/{id}", 99999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTeacher() throws Exception {
        School school = createSampleSchool("Test School 8");
        Teacher teacher = createSampleTeacher("Teacher I", "TI001", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        mockMvc.perform(delete("/api/teachers/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNonExistentTeacher() throws Exception {
        mockMvc.perform(delete("/api/teachers/{id}", 99999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testTeacherExists() throws Exception {
        School school = createSampleSchool("Test School 9");
        Teacher teacher = createSampleTeacher("Teacher J", "TJ001", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        mockMvc.perform(get("/api/teachers/exists/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(true)));
    }

    @Test
    public void testTeacherDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/teachers/exists/{id}", 99999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(false)));
    }

    @Test
    public void testGetTeacherCount() throws Exception {
        mockMvc.perform(get("/api/teachers/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", greaterThanOrEqualTo(0)));
    }

    @Test
    public void testCreateTeacherWithCompleteDetails() throws Exception {
        School school = createSampleSchool("Test School 10");
        Teacher teacher = new Teacher();
        teacher.setName("Complete Teacher");
        teacher.setEmployeeId("COMPLETE001");
        teacher.setEmail("complete@school.com");
        teacher.setPhoneNumber("8765432109");
        teacher.setAddress("Complete Address");
        teacher.setQualification("B.Tech");
        teacher.setSpecialization("Computer Science");
        teacher.setSchool(school);

        mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Complete Teacher")))
                .andExpect(jsonPath("$.qualification", equalTo("B.Tech")))
                .andExpect(jsonPath("$.specialization", equalTo("Computer Science")));
    }

    @Test
    public void testUpdateTeacherWithMultipleFields() throws Exception {
        School school = createSampleSchool("Test School 11");
        Teacher teacher = createSampleTeacher("Teacher K", "TK001", school);
        Teacher savedTeacher = teacherRepository.save(teacher);

        Teacher updateData = new Teacher();
        updateData.setName("Updated Teacher K");
        updateData.setEmail("updated.k@school.com");
        updateData.setPhoneNumber("7777777777");
        updateData.setQualification("M.Tech");
        updateData.setSpecialization("AI and ML");

        mockMvc.perform(put("/api/teachers/{id}", savedTeacher.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Updated Teacher K")))
                .andExpect(jsonPath("$.email", equalTo("updated.k@school.com")))
                .andExpect(jsonPath("$.qualification", equalTo("M.Tech")))
                .andExpect(jsonPath("$.specialization", equalTo("AI and ML")));
    }

    @Test
    public void testCRUDOperations() throws Exception {
        School school = createSampleSchool("Test School 12");

        // CREATE
        Teacher newTeacher = createSampleTeacher("CRUD Teacher", "CRUD001", school);
        String response = mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTeacher)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Teacher createdTeacher = objectMapper.readValue(response, Teacher.class);
        Long teacherId = createdTeacher.getId();

        // READ
        mockMvc.perform(get("/api/teachers/{id}", teacherId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("CRUD Teacher")));

        // UPDATE
        Teacher updateData = new Teacher();
        updateData.setEmail("crud.updated@school.com");
        mockMvc.perform(put("/api/teachers/{id}", teacherId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("crud.updated@school.com")));

        // DELETE
        mockMvc.perform(delete("/api/teachers/{id}", teacherId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // VERIFY DELETION
        mockMvc.perform(get("/api/teachers/{id}", teacherId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

