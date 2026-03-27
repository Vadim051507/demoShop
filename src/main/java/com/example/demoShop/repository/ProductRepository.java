package com.example.demoShop.repository;

import com.example.demoShop.entity.Product;
import  org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.data.jpa.repository.Modifying;
import  org.springframework.data.jpa.repository.Query;
import  org.springframework.data.repository.query.Param;
import  java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAvailableTrue();
    List<Product> findByCategoryAndAvailableTrue(String category);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :qty WHERE p.id = :id AND p.stock >= :qty")
    int decreaseStock(@Param("id") Long id, @Param("qty") int qty);

}
