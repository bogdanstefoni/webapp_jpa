package com.bogdan.webapp.controller;

import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.findAll();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {
        Student student = studentService.findById(studentId);

        if (student == null) {
            throw new RuntimeException("Employee is not found " + studentId);
        }

        return student;
    }

    @PostMapping("/students/register")
    public Student addStudent(@Valid @RequestBody Student newStudent) {
        List<Student> students = studentService.findAll();

        System.out.println("New Student " + newStudent.toString());

        for (Student student : students) {
            System.out.println("Registered student: " + newStudent.toString());

            if (super.equals(newStudent)) {
                throw new RuntimeException("Student already exists " + newStudent);
            }
        }

        newStudent.setId(0);

        studentService.save(newStudent);

        return newStudent;
    }

    @PostMapping("/students/login")
    public Student loginUser(@Valid @RequestBody Student student) {
        List<Student> students = studentService.findAll();

        for (Student theStudent : students) {
            if (theStudent.equals(student)) {
                System.out.println("Student: " + student + " logged in");
                studentService.save(student);
            } else {
                throw new RuntimeException("Incorrect credentials");
            }
        }
        return student;

    }

    @PutMapping("/students")
    public Student saveStudent(@RequestBody Student student) {

        studentService.save(student);

        return student;
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable int studentId) {
        Student student = studentService.findById(studentId);

        if (student == null) {
            throw new RuntimeException("Students does not exist " + studentId);
        }

        studentService.deleteById(studentId);

        return "Deleted student id  " + studentId;
    }
}
