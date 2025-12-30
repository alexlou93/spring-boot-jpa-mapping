package com.test01.jpa_uni.repository;

import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
