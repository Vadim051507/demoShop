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
}
