package com.Enoca_Challenge.Enoca.service;
import com.Enoca_Challenge.Enoca.entity.Student;
import com.Enoca_Challenge.Enoca.entity.Cart;
import com.Enoca_Challenge.Enoca.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // Müşteri ekleme (AddCustomer)
    public Student addCustomer(Student student) {
        // Email kontrolü
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı: " + student.getEmail());
        }

        // Boş sepet oluştur
        Cart cart = new Cart();
        cart.setStudent(student);
        student.setCart(cart);

        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + id));
    }
}
