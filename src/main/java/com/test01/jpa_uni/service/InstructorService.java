package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.entity.Instructor;

import java.util.List;

public interface InstructorService{

    InstructorDTO findInstructorById(int id);

    InstructorDTO saveInstructor(InstructorDTO instructorDTO);

    void deleteInstructor(int id);

    void updateInstructor(int instructorId, InstructorDTO instructorDTO);

    List<CourseDTO> fetchCourseByInstructorId(int instructorId);

}
