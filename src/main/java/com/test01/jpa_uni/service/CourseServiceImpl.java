package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.exception.CourseNotFoundException;
import com.test01.jpa_uni.exception.InstructorNotFoundException;
import com.test01.jpa_uni.repository.CourseRepository;
import com.test01.jpa_uni.repository.InstructorRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private InstructorRepository instructorRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository theCourseRepository, InstructorRepository instructorRepository){
        courseRepository = theCourseRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<CourseDTO> findCoursesbyInstructorId(int id) {

        // check if instructor exist
        boolean instructorExists = instructorRepository.existsById(id);
        if (!instructorExists) {
            throw new InstructorNotFoundException(
                    "Instructor not found with id : " + id
            );
        }
        // fetch courses using instructor id
        List<Course> courseList = courseRepository.findCourses(id);

        // check if courses are empty or null
        if(courseList == null || courseList.isEmpty())
        {
            throw new CourseNotFoundException(
                    "No courses found for instructor id : " + id);
        }

        List<CourseDTO> dto = new ArrayList<>();

        for(Course course : courseList){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
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
