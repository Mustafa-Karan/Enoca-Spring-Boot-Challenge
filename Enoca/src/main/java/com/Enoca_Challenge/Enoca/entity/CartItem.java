package com.Enoca_Challenge.Enoca.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem extends Base_Entity {

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}