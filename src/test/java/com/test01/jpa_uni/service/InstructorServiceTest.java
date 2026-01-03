package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.dto.InstructorDetailDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.entity.InstructorDetail;
import com.test01.jpa_uni.exception.InstructorNotFoundException;
import com.test01.jpa_uni.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {

    @Mock
    private InstructorRepository  instructorRepository;

    @InjectMocks
    private InstructorServiceImpl instructorService;

    @Test
    void saveInstructor_shouldSaveInstructorWithDetailsAndCourses() {

        // ---------- Arrange ----------
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setFirstName("Alex");
        instructorDTO.setLastName("Brown");
        instructorDTO.setEmail("alex@test.com");

        // Instructor Detail DTO
        InstructorDetailDTO detailDTO = new InstructorDetailDTO();
        detailDTO.setYoutubeChannel("youtube.com/alex");
        detailDTO.setHobby("Teaching");
        instructorDTO.setInstructorDetail(detailDTO);

        // Course DTOs
        CourseDTO course1 = new CourseDTO();
        course1.setTitle("Spring Boot");

        CourseDTO course2 = new CourseDTO();
        course2.setTitle("Hibernate");

        instructorDTO.setCourses(List.of(course1, course2));

        // Mock saved Instructor ... This object represents what the database would return after saving
        Instructor savedInstructor = new Instructor();
        savedInstructor.setInstructorId(1);

        /** When someone calls save() on this mocked repository
        with any Instructor object, return savedInstructor, Simulates JPA behavior: Assigns ID and
         Returns persisted entity **/
        when(instructorRepository.save(ArgumentMatchers.any(Instructor.class)))
                .thenReturn(savedInstructor);

        // ---------- Act ----------
        InstructorDTO result = instructorService.saveInstructor(instructorDTO);

        // ---------- Assert ----------
        assertNotNull(result);
        assertEquals(1, result.getInstructorId());


        /** No duplicate saves ... No missing save
        Confirms correct service → repository interaction **/
        verify(instructorRepository, times(1)).save(any(Instructor.class));

    }

    // Test case 1 for finding instructor by id ... positive case
    @Test
    void findInstructorById_shouldFetchInstructorDetailAndCourses(){

        int instructor_id = 3;

        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructor_id);
        instructor.setFirstName("Kevin");
        instructor.setLastName("Jones");
        instructor.setEmail("kevin.jones@luve2cook.com");

        InstructorDetail detail = new InstructorDetail();
        detail.setHobby("Cooking");
        detail.setYoutube_channel("Authentic Cooking Explorer");

        instructor.setInstructorDetail(detail);

        Course course1 = new Course();
        course1.setId(201);
        course1.setTitle("Environmental Science");

        Course course2 = new Course();
        course2.setId(203);
        course2.setTitle("Statistics");

        instructor.setCourses(List.of(course1,course2));


        when(instructorRepository.findById(instructor_id)).thenReturn(Optional.of(instructor));

        InstructorDTO result = instructorService.findInstructorById(instructor_id);

        assertNotNull(result);
        assertEquals("Kevin", result.getFirstName());

        verify(instructorRepository, times(1)).findById(instructor_id);
    }

    // Test case 2 for finding instructor by id ... negative case (InstructorNotFound Exception)
    @Test
    void findInstructorById_InstructorNotFoundException(){

        int instructorId = 33;

        when(instructorRepository.findById(instructorId)).
                thenReturn(Optional.empty());

        InstructorNotFoundException exception =
                assertThrows(InstructorNotFoundException.class,
                        ()-> instructorService.findInstructorById(instructorId));

        assertEquals("Instructor not found : " + instructorId, exception.getMessage());

        verify(instructorRepository, times(1)).findById(instructorId);
    }



    // Test Case 1 for Delete: Successfully delete instructor and detach courses
    @Test
    void deleteInstructor_shouldDeleteInstructor(){

        int instructorId = 1;

        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorId);

        Course course1 = new Course();
        course1.setInstructor(instructor);

        Course course2 = new Course();
        course2.setInstructor(instructor);

        instructor.setCourses(List.of(course1,course2));

        when(instructorRepository.findById(instructorId)).
                thenReturn(Optional.of(instructor));

        instructorService.deleteInstructor(instructorId);

        // Courses should be detached
        assertNull(course1.getInstructor());
        assertNull(course2.getInstructor());

        // Instructor should be deleted
        verify(instructorRepository, times(1)).delete(instructor);

    }

    // Test Case 2 for Delete: Instructor not found → Exception thrown
    @Test
    void deleteInstructor_instructorNotFound(){

        int instructorId = 66;

        when(instructorRepository.findById(instructorId)).
                thenReturn(Optional.empty());

        InstructorNotFoundException exception =
                assertThrows(InstructorNotFoundException.class, () ->
                        instructorService.deleteInstructor(instructorId));

        assertEquals("Instructor not found : " + instructorId, exception.getMessage());

        // delete should never be called
        verify(instructorRepository, never()).delete(any());
    }
}



