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

    // Sipariş ver (PlaceOrder)
    public CustomerOrder placeOrder(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı: " + studentId));

        Cart cart = cartService.getCart(studentId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Sepet boş");
        }

        // Sipariş oluştur
        CustomerOrder order = new CustomerOrder();
        order.setStudent(student);
        order.setStatus(CustomerOrder.OrderStatus.CONFIRMED);
        order.setItems(new HashSet<>());

        CustomerOrder savedOrder = customerOrderRepository.save(order);

        // Sepet itemlarını siparişe aktar ve öğrenciyi kurslara kaydet
        for (CartItem cartItem : cart.getItems()) {
            // OrderItem oluştur
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(savedOrder);
            orderItem.setCourse(cartItem.getCourse());
            orderItem.setPrice(cartItem.getPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedOrder.getItems().add(savedOrderItem);

            // Öğrenciyi kursa kaydet
            Course course = cartItem.getCourse();
            if (course.getEnrolledStudents() == null) {
                course.setEnrolledStudents(new HashSet<>());
            }
            course.getEnrolledStudents().add(student);
            course.setCurrentStudents(course.getCurrentStudents() + 1);
            courseRepository.save(course);

            // Student'in courses listesine de ekle
            if (student.getCourses() == null) {
                student.setCourses(new HashSet<>());
            }
            student.getCourses().add(course);
        }

        // Öğrenciyi kaydet
        studentRepository.save(student);

        // Toplam fiyatı hesapla ve kaydet
        savedOrder.calculateTotalPrice();
        savedOrder = customerOrderRepository.save(savedOrder);

        // Sepeti boşalt
        cartService.emptyCart(studentId);

        return savedOrder;
    }

    // Sipariş koduna göre getir (GetOrderForCode)
    public CustomerOrder getOrderForCode(String orderCode) {
        return customerOrderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı: " + orderCode));
    }

    // Müşterinin tüm siparişleri (GetAllOrdersForCustomer)
    public List<CustomerOrder> getAllOrdersForCustomer(Long studentId) {
        return customerOrderRepository.findByStudentId(studentId);
    }

    // Tüm siparişleri getir (Admin için)
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }
}