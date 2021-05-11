package com.viniciusog.springsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Fernando Lima"),
            new Student(2, "Maria Alves"),
            new Student(3, "Giovanna Vasco")
    );

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable Integer studentId) {
        return STUDENTS.stream().filter(student -> student.getStudentId() == studentId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exists."));
    }
}
