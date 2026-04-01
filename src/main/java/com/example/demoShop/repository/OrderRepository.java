package com.example.demoShop.repository;

import com.example.demoShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findTop5ByOrderByCreatedAtDesc();
    List<Order> findAllByOrderByCreatedAtDesc();
}