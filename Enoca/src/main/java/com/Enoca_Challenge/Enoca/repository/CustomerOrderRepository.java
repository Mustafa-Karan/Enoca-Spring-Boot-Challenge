package com.Enoca_Challenge.Enoca.repository;

import com.Enoca_Challenge.Enoca.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByOrderCode(String orderCode);
    List<CustomerOrder> findByStudentId(Long studentId);
}