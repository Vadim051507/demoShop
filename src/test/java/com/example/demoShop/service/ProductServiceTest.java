package com.example.demoShop.service;

import com.example.demoShop.entity.Product;
import com.example.demoShop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnAllProductsWhenCategoryIsAll() {
        when(productRepository.findByAvailableTrue()).thenReturn(List.of(buildProduct("bouquets"), buildProduct("plants")));

        List<Product> result = productService.getProducts("all");

        assertEquals(2, result.size());
        verify(productRepository).findByAvailableTrue();
    }

    @Test
    void shouldReturnAllProductsWhenCategoryIsNull() {
        when(productRepository.findByAvailableTrue()).thenReturn(List.of(buildProduct("bouquets")));

        List<Product> result = productService.getProducts(null);

        assertEquals(1, result.size());
        verify(productRepository).findByAvailableTrue();
    }

    @Test
    void shouldReturnAllProductsWhenCategoryIsBlank() {
        when(productRepository.findByAvailableTrue()).thenReturn(List.of(buildProduct("bouquets")));

        List<Product> result = productService.getProducts("   ");

        assertEquals(1, result.size());
        verify(productRepository).findByAvailableTrue();
    }

    @Test
    void shouldFilterByCategory() {
        when(productRepository.findByCategoryAndAvailableTrue("bouquets"))
             .thenReturn(List.of(buildProduct("bouquets")));

        List<Product> result = productService.getProducts("bouquets");

        assertEquals(1, result.size());
        assertEquals("bouquets", result.get(0).getCategory());
        verify(productRepository).findByCategoryAndAvailableTrue("bouquets");

    }

    @Test
    void shouldReturnEmptyListWhenNoCategoryMatch() {
        when(productRepository.findByCategoryAndAvailableTrue("exotic")).thenReturn(List.of());

        List<Product> result = productService.getProducts("exotic");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNotCallCategoryFilterWhenCategoryIsAll() {
        when(productRepository.findByAvailableTrue()).thenReturn(List.of());

        productService.getProducts("all");

        verify(productRepository, never()).findByCategoryAndAvailableTrue(any());
    }

    private Product buildProduct(String category) {
        Product p = new Product();
        p.setName("Тестовий букет");
        p.setPrice(500);
        p.setCategory(category);
        p.setStock(10);
        return p;
    }
}
