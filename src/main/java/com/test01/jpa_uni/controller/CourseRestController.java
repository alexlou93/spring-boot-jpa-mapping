package com.test01.jpa_uni.controller;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseRestController {

        private final CourseService courseService;

        @Autowired
        public CourseRestController(CourseService courseService) {
            this.courseService = courseService;
        }

        @GetMapping("/course/{id}")
        public List<CourseDTO> getCoursesByInstructorId(@PathVariable int id) {
            return courseService.findCoursesbyInstructorId(id);
        }

        @DeleteMapping("/course/{title}")
        ResponseEntity<String> deleteCourseByTitle(@PathVariable String title){

            courseService.deleteCourse(title);
            return  ResponseEntity.ok("Course deleted : " + title);
        }
    }


