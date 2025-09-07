package com.Enoca_Challenge.Enoca.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
public class Student extends Base_Entity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Course> courses = new HashSet<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<CustomerOrder> orders = new HashSet<>();
}