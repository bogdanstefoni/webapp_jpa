package com.bogdan.webapp.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(int id) {
        super(String.format("Student with Id %d not found", id));
    }
}
