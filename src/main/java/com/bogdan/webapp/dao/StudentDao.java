package com.bogdan.webapp.dao;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentDao {

    private StudentRepository genericRepository;

    private Logger logger = LoggerFactory.getLogger(Student.class);

    @Autowired
    public StudentDao(StudentRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    public List<Student> findAll() {
        return genericRepository.findAll();
    }

    public Student findById(int id) {
        Optional<Student> result = genericRepository.findById(id);

        Student student = null;

        if (result.isPresent()) {
            student = result.get();
        } else {
            throw new RuntimeException("Student id: " + id + " not found");
        }
        return student;
    }
}
