package com.bogdan.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping()
	public List<Student> getStudents() {
		return studentService.findAll();
	}

	@GetMapping("/{studentId}")
	public Student getStudentById(@PathVariable int studentId) {
		Student student = studentService.findById(studentId);

		if (student == null) {
			throw new RuntimeException("Employee is not found " + studentId);
		}

		return student;
	}

	@PostMapping("/register")
	public Student register(@Valid @RequestBody Student newStudent) {
		return studentService.register(newStudent);
	}

//	@PostMapping("login")
//	public Student loginUser(@Valid @RequestBody Student student) {
//		List<Student> students = studentService.findAll();
//
//		for (Student theStudent : students) {
//			if (theStudent.equals(student)) {
//				System.out.println("Student: " + student + " logged in");
//				studentService.save(student);
//			} else {
//				throw new RuntimeException("Incorrect credentials");
//			}
//		}
//		return student;
//
//	}

//	@PutMapping()
//	public Student updateStudent(@RequestBody Student student) {
//
//		studentService.save(student);
//
//		return student;
//	}

	@DeleteMapping("{studentId}")
	public String deleteStudent(@PathVariable int studentId) {
		Student student = studentService.findById(studentId);

		if (student == null) {
			throw new RuntimeException("Students does not exist " + studentId);
		}

		studentService.deleteById(studentId);

		return "Deleted student id  " + studentId;
	}
}
