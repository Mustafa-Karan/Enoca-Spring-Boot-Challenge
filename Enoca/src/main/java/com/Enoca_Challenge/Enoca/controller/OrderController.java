package com.Enoca_Challenge.Enoca.controller;

import com.Enoca_Challenge.Enoca.entity.CustomerOrder;
import com.Enoca_Challenge.Enoca.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Place order
    @PostMapping("/students/{studentId}/orders")
    public ResponseEntity<CustomerOrder> placeOrder(@PathVariable Long studentId) {
        try {
            CustomerOrder order = orderService.placeOrder(studentId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get order by code
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity<CustomerOrder> getOrderForCode(@PathVariable String orderCode) {
        try {
            CustomerOrder order = orderService.getOrderForCode(orderCode);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all orders for customer
    @GetMapping("/students/{studentId}/orders")
    public ResponseEntity<List<CustomerOrder>> getAllOrdersForCustomer(@PathVariable Long studentId) {
        try {
            List<CustomerOrder> orders = orderService.getAllOrdersForCustomer(studentId);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}