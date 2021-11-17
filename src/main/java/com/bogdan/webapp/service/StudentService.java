package com.bogdan.webapp.service;

import com.bogdan.webapp.dto.StudentDto;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<?> findAll();

    ResponseEntity<String> findById();

    ResponseEntity<String> findByUsername(String username);

    ResponseEntity<String> register(StudentDto studentDto);

    ResponseEntity<String> login(StudentDto studentDto);

    ResponseEntity<String> update(StudentDto studentDto);

    void deleteById(int id);

    void deleteByUsername(String username);

}
