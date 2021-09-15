package com.bogdan.webapp.service;

import com.bogdan.webapp.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(int id);

    void save(Student student);

    void deleteById(int id);


}
