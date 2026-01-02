package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.dto.InstructorDetailDTO;
import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}


