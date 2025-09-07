package com.Enoca_Challenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customerOrder", "course"})
@ToString(exclude = {"customerOrder"})
public class OrderItem extends Base_Entity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}