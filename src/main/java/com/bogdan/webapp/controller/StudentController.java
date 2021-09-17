package com.bogdan.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.service.StudentService;

@RestController
@RequestMapping("/students")
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
		return studentService.findById(studentId);
	}

	@GetMapping("/username/{username}")
	public Student getStudentByUsername(@PathVariable String username) {
		return studentService.findByUsername(username);
	}

	@PostMapping("/register")
	public Student register(@Valid @RequestBody Student newStudent) {
		return studentService.register(newStudent);
	}

	@PostMapping("/login")
	public void loginUser(@Valid @RequestBody Student student) {

		studentService.login(student);

	}

	@PutMapping("")
	public Student updateStudent(@RequestBody Student student) {

		studentService.update(student);

		return student;
	}

	@DeleteMapping("/{studentId}")
	public void deleteStudent(@PathVariable int studentId) {
		studentService.deleteById(studentId);
	}

	@DeleteMapping("/username/{username}")
	public void deleteStudent(@PathVariable String username) {
		studentService.deleteByUsername(username);
	}
}
