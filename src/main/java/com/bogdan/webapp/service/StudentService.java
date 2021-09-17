package com.bogdan.webapp.service;

import java.util.List;

import com.bogdan.webapp.entity.Student;

public interface StudentService {

	List<Student> findAll();

	Student findById(int id);

	Student findByUsername(String username);

	Student register(Student student);

	void login(Student student);

	void update(Student student);

	void deleteById(int id);

	void deleteByUsername(String username);

}
