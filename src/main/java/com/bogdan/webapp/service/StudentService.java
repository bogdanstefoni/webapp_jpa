package com.bogdan.webapp.service;

import java.util.List;

import com.bogdan.webapp.dto.StudentDto;
import com.bogdan.webapp.entity.Student;
import org.springframework.http.ResponseEntity;

public interface StudentService {

	ResponseEntity<String> findAll();

	ResponseEntity<String> findById(int id);

	ResponseEntity<String> findByUsername(String username);

	ResponseEntity<String>  register(StudentDto studentDto);

	ResponseEntity<String>  login(StudentDto studentDto);

	void update(Student student);

	void deleteById(int id);

	void deleteByUsername(String username);

}
