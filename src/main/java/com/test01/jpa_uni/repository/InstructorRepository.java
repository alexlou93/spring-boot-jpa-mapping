package com.test01.jpa_uni.repository;

import com.test01.jpa_uni.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}
