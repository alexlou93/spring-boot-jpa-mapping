package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.exception.CourseNotFoundException;
import com.test01.jpa_uni.exception.InstructorNotFoundException;
import com.test01.jpa_uni.repository.CourseRepository;
import com.test01.jpa_uni.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void findCoursesbyInstructorId_success() {

        int instructorId = 1;
        Course course1 = new Course();
        course1.setId(101);
        course1.setTitle("Biology");

        Course course2 = new Course();
        course2.setId(102);
        course2.setTitle("Chemistry");

        List<Course> courses = List.of(course1,course2);

        when(instructorRepository.existsById(instructorId)).thenReturn(true);
        when(courseRepository.findCourses(instructorId)).thenReturn(courses);

        // Act
        List<CourseDTO> result = courseService.findCoursesbyInstructorId(instructorId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Biology", result.get(0).getTitle());
        assertEquals("Chemistry", result.get(1).getTitle());

        verify(instructorRepository, times(1)).existsById(instructorId);
        verify(courseRepository, times(1)).findCourses(instructorId);
    }


    @Test
    void findCoursesbyInstructorId_instructorNotFound() {

        int instructorId = 1;

        when(instructorRepository.existsById(instructorId)).thenReturn(false);

        // Act + Assert
        InstructorNotFoundException exception =
                assertThrows(InstructorNotFoundException.class,
                        () -> courseService.findCoursesbyInstructorId(instructorId));

        assertEquals("Instructor not found with id : 1", exception.getMessage());

        verify(instructorRepository, times(1)).existsById(instructorId);
        verify(courseRepository, never()).findCourses(anyInt());
    }


    @Test
    void findCoursesByInstructorId_coursesNotFound(){

        int instructorId = 1;

        when(instructorRepository.existsById(instructorId)).thenReturn(TRUE);
        when(courseRepository.findCourses(instructorId)).thenReturn(Collections.emptyList());

        CourseNotFoundException exception =
                assertThrows(CourseNotFoundException.class,
                        () -> courseService.findCoursesbyInstructorId(instructorId));

        assertEquals("No courses found for instructor id : 1", exception.getMessage());

        verify(instructorRepository, times(1)).existsById(instructorId);
        verify(courseRepository, times(1)).findCourses(instructorId);
    }
    }


