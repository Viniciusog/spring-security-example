package com.viniciusog.springsecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
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

    //TODO: Para que @PreAuthorize seja aceito pelo Spring, temos que adicionar a anotação
    // @EnableGlobalMethodSecurity(prePostEnabled = true)

    @GetMapping
    //Permite que apenas os usuário que tenham ROLE de Admin ou Admintrainee tenham acesso a esse método
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }

    @PostMapping
    //Permite que apenas os usuário que tenham permissão de alterar dados de student, possam acessar esse método
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @DeleteMapping("/{studentId}")
    //Permite que apenas os usuário que tenham permissão de alterar dados de student, possam acessar esse método
    @PreAuthorize("hasAuthority('student:write')")
    public void removeStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("Student " + studentId + " removed!");
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Student " + studentId + " updated. New Value: " + student);
    }
}