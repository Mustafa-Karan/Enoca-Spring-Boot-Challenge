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
public class OrderItem extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private CustomerOrder customerOrder;

    @ManyToOne
    private Course course;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}