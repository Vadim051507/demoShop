package com.example.demoShop.controler;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.service.CartService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart() {
        return ResponseEntity.ok(Map.of(
                "items", cartService.getItems(),
                "total", cartService.getTotal(),
                "count", cartService.getCount()
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody CartItem item) {
        cartService.addItem(item);
        return ResponseEntity.ok(Map.of(
                "count", cartService.getCount(),
                "total", cartService.getTotal()
        ));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeItem(@PathVariable Long productId) {
        cartService.removeItem(productId);
        return ResponseEntity.ok(Map.of(
                "count", cartService.getCount(),
                "total", cartService.getTotal()
        ));
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart() {
        cartService.clear();
        return ResponseEntity.ok(Map.of("count", 0, "total", 0));
    }
}
