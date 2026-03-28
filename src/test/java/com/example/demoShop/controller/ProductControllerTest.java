package com.example.demoShop.controller;

import com.example.demoShop.controler.ProductController;
import com.example.demoShop.entity.Product;
import com.example.demoShop.service.ProductService;
import com.example.demoShop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnProductsAsJson() throws Exception {
        when(productService.getProducts("all")).thenReturn(List.of(buildProduct("bouquets", "Хіт")));

        mockMvc.perform(get("/api/products?category=all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Тестовий букет"))
                .andExpect(jsonPath("$[0].cat").value("bouquets"))
                .andExpect(jsonPath("$[0].price").value(500))
                .andExpect(jsonPath("$[0].badge").value("Хіт"));
    }

    @Test
    void shouldReturnEmptyArrayWhenNoProducts() throws Exception {
        when(productService.getProducts("all")).thenReturn(List.of());

        mockMvc.perform(get("/api/products?category=all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnEmptyBadgeWhenNull() throws Exception {
        when(productService.getProducts("all")).thenReturn(List.of(buildProduct("plants", null)));

        mockMvc.perform(get("/api/products?category=all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].badge").value(""));
    }

    @Test
    void shouldReturn200ForCategoryFilter() throws Exception {
        when(productService.getProducts("bouquets")).thenReturn(List.of(buildProduct("bouquets", null)));

        mockMvc.perform(get("/api/products?category=bouquets"))
                .andExpect((status().isOk()));
    }

    private Product buildProduct(String category, String badge) {
        Product p = new Product();
        p.setName("Тестовий букет");
        p.setPrice(500);
        p.setCategory(category);
        p.setBadge(badge);
        p.setStock(10);
        p.setImageUrl("https://example.com/img.jpg");
        return p;
    }
}
