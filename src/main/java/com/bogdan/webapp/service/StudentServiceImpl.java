package com.bogdan.webapp.service;

import com.bogdan.webapp.dao.GenericRepository;
import com.bogdan.webapp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService{

    private GenericRepository studentRepository ;

    @Autowired
    public StudentServiceImpl(GenericRepository studentRepository) {
        this.studentRepository = studentRepository;
    }




    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int id) {
        Optional<Student> result = studentRepository.findById(id);

        Student student = null;

        if(result.isPresent()) {
            student = result.get();
        } else {
            throw new RuntimeException("Could not find student " + id);
        }

        return student;
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }



    @Override
    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }
}
