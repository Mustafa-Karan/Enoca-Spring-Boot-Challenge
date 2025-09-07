package com.Enoca_Challenge.Enoca.service;
import com.Enoca_Challenge.Enoca.entity.Cart;
import com.Enoca_Challenge.Enoca.entity.CartItem;
import com.Enoca_Challenge.Enoca.entity.Course;
import com.Enoca_Challenge.Enoca.entity.Student;
import com.Enoca_Challenge.Enoca.repository.*;
        import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    // Sepeti getir (GetCart)
    public Cart getCart(Long studentId) {
        return cartRepository.findByStudentId(studentId)
                .orElseGet(() -> createEmptyCart(studentId));
    }

    // Sepete kurs ekle (AddCourseToCart)
    public Cart addCourseToCart(Long studentId, Long courseId) {
        Cart cart = getCart(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs bulunamadı: " + courseId));

        // Kurs müsait mi kontrol et
        if (!course.canEnroll()) {
            throw new RuntimeException("Bu kursa kayıt yapılamaz");
        }

        // Zaten sepette var mı kontrol et
        if (cartItemRepository.findByCartIdAndCourseId(cart.getId(), courseId).isPresent()) {
            throw new RuntimeException("Kurs zaten sepette");
        }

        // Sepete ekle
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setCourse(course);
        item.setPrice(course.getPrice());

        cartItemRepository.save(item);
        cart.getItems().add(item);
        cart.calculateTotalPrice();

        return cartRepository.save(cart);
    }

    // Sepetten kurs çıkar (RemoveCourseFromCart)
    public Cart removeCourseFromCart(Long studentId, Long courseId) {
        Cart cart = getCart(studentId);
        cartItemRepository.deleteByCartIdAndCourseId(cart.getId(), courseId);

        cart.getItems().removeIf(item -> item.getCourse().getId().equals(courseId));
        cart.calculateTotalPrice();

        return cartRepository.save(cart);
    }

    // Sepet güncelle (UpdateCart)
    public Cart updateCart(Long studentId) {
        Cart cart = getCart(studentId);
        cart.calculateTotalPrice();
        return cartRepository.save(cart);
    }

    // Sepeti boşalt (EmptyCart)
    public void emptyCart(Long studentId) {
        Cart cart = getCart(studentId);
        cart.getItems().clear();
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    private Cart createEmptyCart(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + studentId));

        Cart cart = new Cart();
        cart.setStudent(student);
        return cartRepository.save(cart);
    }
}