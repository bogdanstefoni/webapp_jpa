package com.bogdan.webapp.service;

import java.util.List;

import com.bogdan.webapp.entity.Student;

public interface StudentService {

	List<Student> findAll();

	Student findById(int id);

	Student register(Student student);

	void deleteById(int id);

}
