package com.bogdan.webapp.service;

import com.bogdan.webapp.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Student findById(int id);

    Optional<Student> findByUsername(String username);

    Student register(Student student);

    void login(Student student);

    void update(Student student);

    void deleteById(int id);

    void deleteByUsername(String username);

}
