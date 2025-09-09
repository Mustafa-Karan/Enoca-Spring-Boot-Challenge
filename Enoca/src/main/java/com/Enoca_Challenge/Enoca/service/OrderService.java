package com.Enoca_Challenge.Enoca.service;

import com.Enoca_Challenge.Enoca.entity.*;
import com.Enoca_Challenge.Enoca.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Place order from cart
    public CustomerOrder placeOrder(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        Cart cart = cartService.getCart(studentId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create order
        CustomerOrder order = new CustomerOrder();
        order.setStudent(student);
        order.setStatus(CustomerOrder.OrderStatus.CONFIRMED);
        order.setItems(new HashSet<>());

        CustomerOrder savedOrder = customerOrderRepository.save(order);

        // Transfer cart items to order and enroll students in courses
        for (CartItem cartItem : cart.getItems()) {
            // Verify course is still available and teacher is active
            Course course = cartItem.getCourse();
            if (!course.canEnroll()) {
                throw new RuntimeException("Course no longer available: " + course.getTitle());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(savedOrder);
            orderItem.setCourse(course);
            orderItem.setPrice(cartItem.getPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedOrder.getItems().add(savedOrderItem);

            // Enroll student in course
            if (course.getEnrolledStudents() == null) {
                course.setEnrolledStudents(new HashSet<>());
            }
            course.getEnrolledStudents().add(student);
            course.setCurrentStudents(course.getCurrentStudents() + 1);
            courseRepository.save(course);

            // Add course to student's enrolled courses
            if (student.getCourses() == null) {
                student.setCourses(new HashSet<>());
            }
            student.getCourses().add(course);
        }

        // Save student
        studentRepository.save(student);

        // Calculate total price and save order
        savedOrder.calculateTotalPrice();
        savedOrder = customerOrderRepository.save(savedOrder);

        // Empty cart
        cartService.emptyCart(studentId);

        return savedOrder;
    }

    // Get order by code
    public CustomerOrder getOrderForCode(String orderCode) {
        return customerOrderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderCode));
    }

    // Get all orders for customer
    public List<CustomerOrder> getAllOrdersForCustomer(Long studentId) {
        return customerOrderRepository.findByStudentId(studentId);
    }

    // Get all orders (for admin)
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }
}