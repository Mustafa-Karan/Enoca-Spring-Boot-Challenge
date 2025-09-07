package com.Enoca_Challenge.Enoca.controller;

import com.Enoca_Challenge.Enoca.entity.Student;
import com.Enoca_Challenge.Enoca.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Müşteri ekleme (AddCustomer)
    @PostMapping
    public ResponseEntity<Student> addCustomer(@RequestBody Student student) {
        Student createdStudent = studentService.addCustomer(student);
        return ResponseEntity.ok(createdStudent);
    }

    // Tüm müşterileri getir
    @GetMapping
    public ResponseEntity<List<Student>> getAllCustomers() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Tek müşteri getir
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
}