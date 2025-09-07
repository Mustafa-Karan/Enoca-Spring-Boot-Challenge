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

    // Sepeti getir (GetCart)
    @GetMapping
    public ResponseEntity<Cart> getCart(@PathVariable Long studentId) {
        Cart cart = cartService.getCart(studentId);
        return ResponseEntity.ok(cart);
    }

    // Sepete kurs ekle (AddCourseToCart)
    @PostMapping("/courses/{courseId}")
    public ResponseEntity<Cart> addCourseToCart(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        Cart cart = cartService.addCourseToCart(studentId, courseId);
        return ResponseEntity.ok(cart);
    }

    // Sepetten kurs çıkar (RemoveCourseFromCart)
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Cart> removeCourseFromCart(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        Cart cart = cartService.removeCourseFromCart(studentId, courseId);
        return ResponseEntity.ok(cart);
    }

    // Sepet güncelle (UpdateCart)
    @PutMapping
    public ResponseEntity<Cart> updateCart(@PathVariable Long studentId) {
        Cart cart = cartService.updateCart(studentId);
        return ResponseEntity.ok(cart);
    }

    // Sepeti boşalt (EmptyCart)
    @DeleteMapping
    public ResponseEntity<String> emptyCart(@PathVariable Long studentId) {
        cartService.emptyCart(studentId);
        return ResponseEntity.ok("Sepet boşaltıldı");
    }
}