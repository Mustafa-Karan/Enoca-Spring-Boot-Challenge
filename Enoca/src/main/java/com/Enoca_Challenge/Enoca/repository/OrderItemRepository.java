package com.Enoca_Challenge.Enoca.repository;
import com.Enoca_Challenge.Enoca.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}