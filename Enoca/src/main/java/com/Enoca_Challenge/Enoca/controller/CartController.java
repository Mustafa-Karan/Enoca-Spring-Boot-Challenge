package com.Enoca_Challenge.Enoca.controller;

import com.Enoca_Challenge.Enoca.entity.Cart;
import com.Enoca_Challenge.Enoca.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/{studentId}/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Get cart
    @GetMapping
    public ResponseEntity<Cart> getCart(@PathVariable Long studentId) {
        try {
            Cart cart = cartService.getCart(studentId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Add course to cart
    @PostMapping("/courses/{courseId}")
    public ResponseEntity<Cart> addCourseToCart(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            Cart cart = cartService.addCourseToCart(studentId, courseId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Remove course from cart
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Cart> removeCourseFromCart(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            Cart cart = cartService.removeCourseFromCart(studentId, courseId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update cart
    @PutMapping
    public ResponseEntity<Cart> updateCart(@PathVariable Long studentId) {
        try {
            Cart cart = cartService.updateCart(studentId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Empty cart
    @DeleteMapping
    public ResponseEntity<String> emptyCart(@PathVariable Long studentId) {
        try {
            cartService.emptyCart(studentId);
            return ResponseEntity.ok("Cart emptied successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}