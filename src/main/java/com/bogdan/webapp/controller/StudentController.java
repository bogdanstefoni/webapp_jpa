package com.bogdan.webapp.controller;

import com.bogdan.webapp.dto.StudentDto;
import com.bogdan.webapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<String> getStudents() {
        return studentService.findAll();
    }

    @GetMapping("/userId")
    public ResponseEntity<String> getStudentById() {
        return studentService.findById();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<String> getStudentByUsername(
            @PathVariable String username) {
        return studentService.findByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody StudentDto studentDto) {
        return studentService.register(studentDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @Valid @RequestBody StudentDto studentDto) {

        return studentService.login(studentDto);

    }

    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudent(
            @RequestBody StudentDto studentDto, @PathVariable int studentId) {

        studentDto.setId(studentId);

        return studentService.update(studentDto);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable int studentId) {
        studentService.deleteById(studentId);
    }

    @DeleteMapping("/username/{username}")
    public void deleteStudent(
            @PathVariable String username) {
        studentService.deleteByUsername(username);
    }
}
