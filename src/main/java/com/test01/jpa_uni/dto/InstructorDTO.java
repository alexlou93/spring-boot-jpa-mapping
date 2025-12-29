package com.test01.jpa_uni.dto;

import java.util.List;

public class InstructorDTO {

    private int instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private InstructorDetailDTO instructorDetail;
    private List<CourseDTO> courses;

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstructorDetailDTO getInstructorDetail() {
        return instructorDetail;
    }

    public void setInstructorDetail(InstructorDetailDTO instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

}
