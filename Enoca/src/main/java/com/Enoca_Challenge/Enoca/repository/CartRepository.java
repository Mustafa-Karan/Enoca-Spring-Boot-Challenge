package com.Enoca_Challenge.Enoca.repository;

import com.Enoca_Challenge.Enoca.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByStudentId(Long studentId);
}