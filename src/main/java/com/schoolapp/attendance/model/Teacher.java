package com.schoolapp.attendance.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String employeeId;

    private String email;

    private String phoneNumber;

    private String address;

    private String qualification;

    private String specialization;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "classTeacher", cascade = CascadeType.ALL)
    private List<SchoolClass> classesTeaching;

    @OneToMany(mappedBy = "sectionTeacher", cascade = CascadeType.ALL)
    private List<ClassSection> sectionsTeaching;
}

