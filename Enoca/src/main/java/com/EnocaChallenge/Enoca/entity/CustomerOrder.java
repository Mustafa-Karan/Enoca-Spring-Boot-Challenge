package com.EnocaChallenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer_orders") // Fixed: Changed from "orders" to avoid PostgreSQL reserved keyword
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"student", "items"})
@ToString(exclude = {"student", "items"})
public class CustomerOrder extends BaseEntity {

    @Column(unique = true)
    private String orderCode = UUID.randomUUID().toString();

    @JsonIgnore
    @ManyToOne
    private Student student;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    public enum OrderStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    // Calculate total price from order items
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}