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

    // Sipariş ver (PlaceOrder)
    @PostMapping("/students/{studentId}/orders")
    public ResponseEntity<CustomerOrder> placeOrder(@PathVariable Long studentId) {
        CustomerOrder order = orderService.placeOrder(studentId);
        return ResponseEntity.ok(order);
    }

    // Sipariş koduna göre getir (GetOrderForCode)
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity<CustomerOrder> getOrderForCode(@PathVariable String orderCode) {
        CustomerOrder order = orderService.getOrderForCode(orderCode);
        return ResponseEntity.ok(order);
    }

    // Müşterinin tüm siparişleri (GetAllOrdersForCustomer)
    @GetMapping("/students/{studentId}/orders")
    public ResponseEntity<List<CustomerOrder>> getAllOrdersForCustomer(@PathVariable Long studentId) {
        List<CustomerOrder> orders = orderService.getAllOrdersForCustomer(studentId);
        return ResponseEntity.ok(orders);
    }
}