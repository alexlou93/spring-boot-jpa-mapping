package com.test01.jpa_uni.controller;

import com.test01.jpa_uni.entity.InstructorDetail;
import com.test01.jpa_uni.service.InstructorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InstructorDetailRestController

{
    private InstructorDetailService instructorDetailService;

    @Autowired
    public InstructorDetailRestController(InstructorDetailService theinstructorDetailService){
        instructorDetailService = theinstructorDetailService;
    }

    @GetMapping("/instructordetail/{instructorId}")
    public InstructorDetail instructorDetail(@PathVariable int instructorId)
    {
        return instructorDetailService.findInstructorDetailById(instructorId);
    }

    @DeleteMapping("/instructordetail/{instructorId}")
    public void deleteInstructorDetailById(@PathVariable int instructorId)
    {
        instructorDetailService.deleteInstructorDetailById(instructorId);
    }
}
