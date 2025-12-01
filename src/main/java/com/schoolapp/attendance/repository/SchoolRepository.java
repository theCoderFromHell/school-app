package com.schoolapp.attendance.repository;

import com.schoolapp.attendance.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    School findByName(String name);
}

