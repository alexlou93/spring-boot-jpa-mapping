package com.test01.jpa_uni.service;

import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.entity.Instructor;

public interface InstructorService{

    InstructorDTO findInstructorById(int id);

    InstructorDTO saveInstructor(InstructorDTO instructorDTO);

    void deleteInstructor(int id);

    void updateInstructor(int instructorId, InstructorDTO instructorDTO);

}
