package com.EnocaChallenge.Enoca.controller;

import com.EnocaChallenge.Enoca.entity.Student;
import com.EnocaChallenge.Enoca.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Add customer (student)
    @PostMapping
    public ResponseEntity<Student> addCustomer(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.addCustomer(student);
            return ResponseEntity.ok(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<Student>> getAllCustomers() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get single customer
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}