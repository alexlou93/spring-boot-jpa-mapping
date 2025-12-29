package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.entity.Course;

import java.util.List;

public interface CourseService {

    List<CourseDTO> findCoursesbyInstructorId(int id);

    public void deleteCourse(String CourseName);
}
