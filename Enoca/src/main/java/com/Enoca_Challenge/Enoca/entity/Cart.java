package com.Enoca_Challenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"student", "items"})
@ToString(exclude = {"student", "items"})
public class Cart extends Base_Entity {

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    // Toplam fiyatı hesapla
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}