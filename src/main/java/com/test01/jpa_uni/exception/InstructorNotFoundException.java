package com.test01.jpa_uni.exception;

public class InstructorNotFoundException extends RuntimeException {
    public InstructorNotFoundException(String message){
        super(message);
    }
}
