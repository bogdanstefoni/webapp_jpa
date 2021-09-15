package com.bogdan.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bogdan.webapp.dao.StudentDao;
import com.bogdan.webapp.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

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
	public Student register(Student newStudent) {
		Optional<Student> existingStudent = studentDao.findByUsername(newStudent.getUsername());
		if (existingStudent.isPresent()) {
			throw new RuntimeException("Student already exists " + newStudent);
		}

		return studentDao.save(newStudent);
	}

	// new login method

	// new updateuser method

	@Override
	public void deleteById(int id) {
		studentDao.delete(id);
	}
}
