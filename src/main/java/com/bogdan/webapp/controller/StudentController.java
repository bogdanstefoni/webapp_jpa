package com.bogdan.webapp.controller;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
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
    public Optional<Student> getStudentByUsername(@PathVariable String username) {
        return studentService.findByUsername(username);
    }

    @PostMapping("/register")
    public Student register(@Valid @RequestBody Student newStudent) {
        return studentService.register(newStudent);
    }

    @PostMapping("/login")
    public Student loginUser(@Valid @RequestBody Student student) {

        return studentService.login(student);

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
}
