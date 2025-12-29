package com.test01.jpa_uni.controller;

import com.test01.jpa_uni.dto.InstructorDTO;
import com.test01.jpa_uni.entity.Instructor;
import com.test01.jpa_uni.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InstructorRestController {

    private InstructorService instructorService;

    @Autowired
    public InstructorRestController(InstructorService theInstructorService){
        instructorService = theInstructorService;
    }

    @GetMapping("/instructor/{instructorId}")
    public InstructorDTO getInstructorById(@PathVariable int instructorId){
        return instructorService.findInstructorById(instructorId);
    }

    @PostMapping("/instructor")
    public ResponseEntity<InstructorDTO> addInstructor(@RequestBody InstructorDTO theInstructorDTO){

        InstructorDTO savedInstructor =
                instructorService.saveInstructor(theInstructorDTO);

        return new  ResponseEntity<>(savedInstructor, HttpStatus.CREATED);


    }

    @DeleteMapping("/instructor/{instructorId}")
    public ResponseEntity<String> deleteById(@PathVariable int instructorId){

        instructorService.deleteInstructor(instructorId);
        return ResponseEntity.ok("Instructor deleted : " + instructorId);
    }


    @PutMapping("/instructor/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable int id,
                                           @RequestBody InstructorDTO instructorDTO){
        instructorService.updateInstructor(id, instructorDTO);

        return ResponseEntity.ok("Instructor updated successfully");
    }
}
