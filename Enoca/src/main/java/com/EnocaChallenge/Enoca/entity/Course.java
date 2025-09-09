package com.EnocaChallenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"teacher", "enrolledStudents", "cartItems", "orderItems"})
@ToString(exclude = {"teacher", "enrolledStudents", "cartItems", "orderItems"})
public class Course extends BaseEntity {

    private String title;

    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Integer maxStudents;

    private Integer currentStudents = 0;

    private Boolean isAvailable = true;

    @ManyToOne
    private Teacher teacher;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> enrolledStudents = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    // Check if course enrollment is available
    public boolean canEnroll() {
        return currentStudents < maxStudents && isAvailable && getIsActive() &&
                teacher != null && teacher.getIsActive();
    }
}