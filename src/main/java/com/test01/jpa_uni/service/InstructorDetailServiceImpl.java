package com.test01.jpa_uni.service;

import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.entity.InstructorDetail;
import com.test01.jpa_uni.repository.InstructorDetailRepository;
import com.test01.jpa_uni.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstructorDetailServiceImpl implements InstructorDetailService{

    private InstructorDetailRepository instructorDetailRepository;

    private InstructorRepository instructorRepository;

    @Autowired
    public InstructorDetailServiceImpl(InstructorDetailRepository theInstructorDetailRepository,
                                       InstructorRepository theinstructorRepository){
        instructorDetailRepository = theInstructorDetailRepository;
        instructorRepository = theinstructorRepository;    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        InstructorDetail instructorDetail = instructorDetailRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Id not available..." + id));
        return instructorDetail;
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        InstructorDetail instructorDetail = instructorDetailRepository.findById(theId)
                        .orElseThrow(() ->
                                new RuntimeException("Id not found.." + theId));

        Instructor instructor = instructorDetail.getInstructor();
        instructorDetailRepository.delete(instructorDetail);

        instructorRepository.delete(instructor);


    }
}
