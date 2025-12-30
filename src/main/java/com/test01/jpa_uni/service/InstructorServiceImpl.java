package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.CourseDTO;
import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.dto.InstructorDetailDTO;
import com.test01.jpa_uni.entity.Course;
import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.entity.InstructorDetail;
import com.test01.jpa_uni.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService{


    private static final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);
    private InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository theInstructorRepository){
        instructorRepository = theInstructorRepository;
    }


    @Override
    @Transactional
    public InstructorDTO saveInstructor(InstructorDTO instructorDTO) {

        Instructor instructor = new Instructor();

        // -------- Save Instructor Fields --------
        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());


        // -------- Save Instructor Detail --------
        InstructorDetail instructorDetail = new InstructorDetail();
        instructorDetail.setYoutube_channel(instructorDTO.getInstructorDetail().getYoutubeChannel());
        instructorDetail.setHobby(instructorDTO.getInstructorDetail().getHobby());

        instructor.setInstructorDetail(instructorDetail);


        // -------- Save Courses --------
        List<Course> courses = new ArrayList<>();
        for(CourseDTO dto : instructorDTO.getCourses())
        {
            Course course = new Course();
            course.setTitle(dto.getTitle());
            courses.add(course);

            instructor.addCourse(course);
        }

        instructor.setCourses(courses);

        Instructor saved = instructorRepository.save(instructor);

        instructorDTO.setInstructorId(saved.getInstructorId());

        return instructorDTO;
    }

    // find instructor by instructor id

    @Override
    public InstructorDTO findInstructorById(int id) {

        Instructor instructor =
                instructorRepository.findById(id).
                        orElseThrow(() -> new RuntimeException("Instructor not found" + id));


        InstructorDTO instructorDTO = new InstructorDTO();

        instructorDTO.setInstructorId(instructor.getInstructorId());
        instructorDTO.setFirstName(instructor.getFirstName());
        instructorDTO.setLastName(instructor.getLastName());
        instructorDTO.setEmail(instructor.getEmail());

        // setting Instructor Details

        InstructorDetail detail = instructor.getInstructorDetail();

        if( detail != null){
            InstructorDetailDTO detailDTO = new InstructorDetailDTO();
            detailDTO.setYoutubeChannel(detail.getYoutube_channel());
            detailDTO.setHobby(detail.getHobby());
            instructorDTO.setInstructorDetail(detailDTO);
        }

        // setting Courses

        List<CourseDTO> courseDTOS = new ArrayList<>();
        for(Course course : instructor.getCourses())
        {
            CourseDTO dto = new CourseDTO();
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            courseDTOS.add(dto);
        }

        instructorDTO.setCourses(courseDTOS);

        return instructorDTO;
    }


    @Override
    @Transactional
    public void deleteInstructor(int id) {

        Instructor instructor =
                instructorRepository.findById(id).orElse(null);

        if (instructor != null){

            // detach course
            for(Course course : instructor.getCourses()){
                course.setInstructor(null);
            }
            System.out.println(instructor);
            instructorRepository.delete(instructor);
        }
    }


    @Override
    @Transactional
    public void updateInstructor(int instructorId, InstructorDTO instructorDTO) {

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found..." + instructorId));


        // -------- Update Instructor Fields --------

        instructor.setFirstName(instructorDTO.getFirstName());
        instructor.setLastName(instructorDTO.getLastName());
        instructor.setEmail(instructorDTO.getEmail());

        // -------- Update Instructor Detail --------

        InstructorDetail detail = instructor.getInstructorDetail();
        if(detail == null){
            detail = new InstructorDetail();
            instructor.setInstructorDetail(detail);
        }
        detail.setYoutube_channel
                (instructorDTO.getInstructorDetail().getYoutubeChannel());
        detail.setHobby
                (instructorDTO.getInstructorDetail().getHobby());

        // -------- Update Courses --------
        instructor.getCourses().clear(); // orphanRemoval = true deletes old courses

        if(instructorDTO.getCourses() != null)
        {
            for(CourseDTO dto : instructorDTO.getCourses()){
                Course course = new Course();
                course.setTitle(dto.getTitle());

                instructor.addCourse(course);
            }
        }

        instructorRepository.save(instructor);
    }

    @Override
    public List<CourseDTO> fetchCourseByInstructorId(int instructorId) {

        Instructor instructor = instructorRepository.findById(instructorId).
                orElseThrow(() -> new RuntimeException("Instructor not available : " +  + instructorId));



        return List.of();
    }
}
