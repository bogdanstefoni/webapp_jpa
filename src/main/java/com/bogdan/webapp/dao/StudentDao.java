package com.bogdan.webapp.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.repo.StudentRepository;

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

	public Optional<Student> findById(int id) {
		Optional<Student> result = genericRepository.findById(id);
		return result;

//		Student student = null;
//
//		if (result.isPresent()) {
//			student = result.get();
//		} else {
//			throw new RuntimeException("Student id: " + id + " not found");
//		}
//		return student;
	}

	public Optional<Student> findByUsername(String username) {
		return genericRepository.findByUsername(username);
	}

	public Student save(Student student) {
		logger.info("Student was saved: " + student.getFirstName());
		return genericRepository.save(student);
	}

	public void delete(int id) {
		logger.info("Deleting student with id " + id);
		genericRepository.deleteById(id);
	}
}
