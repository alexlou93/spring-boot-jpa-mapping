package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.repository.CourseRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository theCourseRepository){
        courseRepository = theCourseRepository;
    }

    @Override
    public List<CourseDTO> findCoursesbyInstructorId(int id) {

        List<Course> courseList = courseRepository.findCourses(id);

        List<CourseDTO> dto = new ArrayList<>();

        for(Course course : courseList){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setTitle(course.getTitle());
            dto.add(courseDTO);
        }

        return dto;
    }

    @Override
    public void deleteCourse(String CourseName) {
        courseRepository.deleteCourse(CourseName);

    }
}
