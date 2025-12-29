package com.test01.jpa_uni.service;

import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.entity.InstructorDetail;

public interface InstructorDetailService {

    InstructorDetail findInstructorDetailById(int id);

    public void deleteInstructorDetailById(int theId);
}
