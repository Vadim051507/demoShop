package com.example.demoShop.controler;

import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.service.CartService;
import com.example.demoShop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest order) {
        try {
            order.setItems(cartService.getItems());
            order.setTotal(cartService.getTotal());

            orderService.processOrder(order);
            cartService.clear();

            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
