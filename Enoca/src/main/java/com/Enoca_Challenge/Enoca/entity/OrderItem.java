package com.Enoca_Challenge.Enoca.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem extends Base_Entity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}