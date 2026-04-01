package com.example.demoShop.repository;

import com.example.demoShop.entity.Order;
import com.example.demoShop.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findTop5ByOrderByCreatedAtDesc();

    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.total), 0) FROM Order o WHERE o.status = 'DONE'")
    long sumRevenue();

    Page<Order> findAll(Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByClientNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Order> findByStatusAndClientNameContainingIgnoreCase(
            OrderStatus status, String name, Pageable pageable);
}