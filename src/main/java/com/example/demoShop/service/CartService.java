package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem newItem) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + 1);
        } else {
            items.add(newItem);
        }
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getTotal() {
        return items.stream()
                .mapToInt(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    public int getCount() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
