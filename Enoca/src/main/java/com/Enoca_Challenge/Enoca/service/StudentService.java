package com.Enoca_Challenge.Enoca.service;

import com.Enoca_Challenge.Enoca.entity.Student;
import com.Enoca_Challenge.Enoca.entity.Cart;
import com.Enoca_Challenge.Enoca.repository.StudentRepository;
import com.Enoca_Challenge.Enoca.repository.CartRepository;
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

    // Müşteri ekleme (AddCustomer)
    public Student addCustomer(Student student) {
        // Email kontrolü
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı: " + student.getEmail());
        }

        // Önce student'i kaydet
        Student savedStudent = studentRepository.save(student);

        // Sonra boş sepet oluştur
        Cart cart = new Cart();
        cart.setStudent(savedStudent);
        cartRepository.save(cart);

        return savedStudent;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}