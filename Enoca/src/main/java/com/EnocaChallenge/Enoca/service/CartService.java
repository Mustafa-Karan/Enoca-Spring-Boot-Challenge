package com.EnocaChallenge.Enoca.service;

import com.EnocaChallenge.Enoca.entity.Cart;
import com.EnocaChallenge.Enoca.entity.CartItem;
import com.EnocaChallenge.Enoca.entity.Course;
import com.EnocaChallenge.Enoca.entity.Student;
import com.EnocaChallenge.Enoca.repository.CartItemRepository;
import com.EnocaChallenge.Enoca.repository.CartRepository;
import com.EnocaChallenge.Enoca.repository.CourseRepository;
import com.EnocaChallenge.Enoca.repository.StudentRepository;
import com.EnocaChallenge.Enoca.repository.*;
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

    // Get cart for student
    public Cart getCart(Long studentId) {
        return cartRepository.findByStudentId(studentId)
                .orElseGet(() -> createEmptyCart(studentId));
    }

    // Add course to cart
    public Cart addCourseToCart(Long studentId, Long courseId) {
        Cart cart = getCart(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        // Check if course can be enrolled (includes teacher active check)
        if (!course.canEnroll()) {
            throw new RuntimeException("Cannot enroll in this course");
        }

        // Check if already in cart
        if (cartItemRepository.findByCartIdAndCourseId(cart.getId(), courseId).isPresent()) {
            throw new RuntimeException("Course already in cart");
        }

        // Add to cart
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setCourse(course);
        item.setPrice(course.getPrice());

        cartItemRepository.save(item);
        cart.getItems().add(item);
        cart.calculateTotalPrice();

        return cartRepository.save(cart);
    }

    // Remove course from cart
    public Cart removeCourseFromCart(Long studentId, Long courseId) {
        Cart cart = getCart(studentId);
        cartItemRepository.deleteByCartIdAndCourseId(cart.getId(), courseId);

        // Reload cart
        cart = cartRepository.findById(cart.getId()).orElse(cart);
        cart.getItems().removeIf(item -> item.getCourse().getId().equals(courseId));
        cart.calculateTotalPrice();

        return cartRepository.save(cart);
    }

    // Update cart
    public Cart updateCart(Long studentId) {
        Cart cart = getCart(studentId);
        cart.calculateTotalPrice();
        return cartRepository.save(cart);
    }

    // Empty cart
    public void emptyCart(Long studentId) {
        Cart cart = getCart(studentId);
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    private Cart createEmptyCart(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        Cart cart = new Cart();
        cart.setStudent(student);
        return cartRepository.save(cart);
    }
}