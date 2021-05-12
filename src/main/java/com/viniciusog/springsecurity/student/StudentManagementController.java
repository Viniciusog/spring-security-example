package com.viniciusog.springsecurity.student;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList (
            new Student(1, "Fernando Lima"),
            new Student(2, "Maria Alves"),
            new Student(3, "Giovanna Vasco")
    );

    @GetMapping
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @DeleteMapping("/{studentId}")
    public void removeStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("Student " + studentId + " removed!");
    }

    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Student " + studentId + " updated. New Value: " + student);
    }
}