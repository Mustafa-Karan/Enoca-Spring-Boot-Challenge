package com.Enoca_Challenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"cart", "course"})
@ToString(exclude = {"cart"})
public class CartItem extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Course course;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}