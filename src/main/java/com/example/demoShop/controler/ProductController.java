package com.example.demoShop.controler;

import com.example.demoShop.entity.Product;
import com.example.demoShop.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Map<String, Object>> getProducts(
            @RequestParam(required = false, defaultValue = "all") String category) {

        return productService.getProducts(category).stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id",    p.getId());
                    map.put("name",  p.getName());
                    map.put("cat",   p.getCategory());
                    map.put("price", p.getPrice());
                    map.put("badge", p.getBadge() != null ? p.getBadge() : "");
                    map.put("img",   p.getImageUrl() != null ? p.getImageUrl() : "");
                    map.put("stock", p.getStock());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
