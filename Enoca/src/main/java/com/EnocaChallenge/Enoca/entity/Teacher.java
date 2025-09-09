package com.EnocaChallenge.Enoca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"courses"})
@ToString(exclude = {"courses"})
public class Teacher extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String specialization;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();
}