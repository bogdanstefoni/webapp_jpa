package com.bogdan.webapp.service;

import com.bogdan.webapp.dao.StudentDao;
import com.bogdan.webapp.entity.Student;
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
        return studentDao.findAll();
    }

    @Override
    public Student findById(int id) {
        Optional<Student> result = studentDao.findById(id);
        Student student = null;

        if (result.isPresent()) {
            student = result.get();
        } else {
            throw new RuntimeException("Could not find student " + id);
        }

        return student;
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

    public Student login(Student student) {
        Optional<Student> existingStudent = studentDao.findByUsername(student.getUsername());

        if (!existingStudent.isPresent()) {
            throw new RuntimeException("Student doesn't exist: " + existingStudent);
        }

        logger.info("Student " + student + " logged in");

        return studentDao.save(student);

    }

    @Override
    public void update(Student student) {


        studentDao.save(student);
    }


    @Override
    public void deleteById(int id) {
        studentDao.deleteById(id);
    }
}
