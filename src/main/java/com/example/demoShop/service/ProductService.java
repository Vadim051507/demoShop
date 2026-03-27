package com.example.demoShop.service;

import com.example.demoShop.entity.Product;
import com.example.demoShop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(String category) {
        if (category == null || category.isBlank() || category.equals("all")) {
            return productRepository.findByAvailableTrue();
        }
        return productRepository.findByCategoryAndAvailableTrue(category);
    }
}
