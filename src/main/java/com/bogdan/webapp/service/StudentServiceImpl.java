package com.bogdan.webapp.service;

import com.bogdan.webapp.dao.StudentDao;
import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.exception.NoDataFoundException;
import com.bogdan.webapp.exception.StudentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> findAll() {
        List<Student> cities = studentDao.findAll();

        if (cities.isEmpty()) {
            throw new NoDataFoundException();
        }

        return cities;
    }

    @Override
    public Student findById(int id) {
        return studentDao.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Optional<Student> findByUsername(String username) {
        return studentDao.findByUsername(username);
    }

    @Override
    public Student register(Student newStudent) {
        Optional<Student> existingStudent = studentDao.findByUsername(newStudent.getUsername());

        if (existingStudent.isPresent()) {
            throw new RuntimeException("Student already exist: " + existingStudent);
        }

        return studentDao.save(newStudent);
    }

    @Override
    public void login(Student student) {

        studentDao.findByUsername(student.getUsername())
                .orElseThrow(() -> new StudentNotFoundException(student.getUsername()));

        logger.info("Student: " + student.getUsername() + " logged in");
    }

    @Override
    public void update(Student student) {

        studentDao.save(student);
    }

    @Override
    public void deleteById(int id) {
        studentDao.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        studentDao.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException(username));
        studentDao.deleteByUsername(username);
    }
}


