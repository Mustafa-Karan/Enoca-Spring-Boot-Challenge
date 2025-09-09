package com.EnocaChallenge.Enoca.repository;

import com.EnocaChallenge.Enoca.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndCourseId(Long cartId, Long courseId);
    void deleteByCartIdAndCourseId(Long cartId, Long courseId);
}