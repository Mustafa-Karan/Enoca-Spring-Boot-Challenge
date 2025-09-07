package com.Enoca_Challenge.Enoca.entity;

import jakarta.persistence .*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "'orders'")
@Data
public class CustomerOrder extends Base_Entity {

    @Column(name = "order_code", unique = true)
    private String orderCode = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    public enum OrderStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    // Toplam fiyatı hesapla
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
