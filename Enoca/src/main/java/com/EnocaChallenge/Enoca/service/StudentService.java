package com.EnocaChallenge.Enoca.service;

import com.EnocaChallenge.Enoca.entity.Student;
import com.EnocaChallenge.Enoca.entity.Cart;
import com.EnocaChallenge.Enoca.repository.StudentRepository;
import com.EnocaChallenge.Enoca.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final CartRepository cartRepository;

    // Add new customer (student)
    public Student addCustomer(Student student) {
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists: " + student.getEmail());
        }

        // Save student first
        Student savedStudent = studentRepository.save(student);

        // Create empty cart for student
        Cart cart = new Cart();
        cart.setStudent(savedStudent);
        cartRepository.save(cart);

        return savedStudent;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}