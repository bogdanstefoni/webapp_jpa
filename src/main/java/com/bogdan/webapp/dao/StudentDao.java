package com.bogdan.webapp.dao;

import com.bogdan.webapp.annotation.LogExecutionTime;
import com.bogdan.webapp.annotation.LogMethodParameters;
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

    @LogExecutionTime
    public List<Student> findAll() {
        return genericRepository.findAll();
    }

    @LogExecutionTime
    public Optional<Student> findById(int id) {
        Optional<Student> result = genericRepository.findById(id);

        return result;
    }

    @LogExecutionTime
    @LogMethodParameters
    public Optional<Student> findByUsername(String username) {
        return genericRepository.findByUsername(username).stream().findFirst();
    }

    public Student save(Student student) {
        logger.info("Student was saved " + student.getUsername());
        return genericRepository.save(student);

    }

    public void deleteById(int id) {
        logger.info("Deleted student with id " + id);
        genericRepository.deleteById(id);
    }
}
