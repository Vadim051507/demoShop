package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.entity.Order;
import com.example.demoShop.entity.OrderItem;
import com.example.demoShop.repository.OrderRepository;
import com.example.demoShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Value("${app.mail.to}")
    private String mailTo;

    public OrderService(EmailService emailService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.emailService = emailService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void processOrder(OrderRequest request) {
        // 1. Зберегти в БД
        Order order = new Order();
        order.setClientName(request.getName());   // твоє поле clientName
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setDeliveryType(request.getDeliveryType());
        order.setComment(request.getComment());
        order.setTotal(request.getTotal());

        for (CartItem cartItem : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductName(cartItem.getName());  // твоє поле productName
            item.setPrice(cartItem.getPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order);
            order.getItems().add(item);
        }

        orderRepository.save(order);

        for (CartItem cartItem : request.getItems()) {
            if (cartItem.getProductId() != null) {
                int updated = productRepository.decreaseStock(
                        cartItem.getProductId(), cartItem.getQuantity()
                );
                if (updated == 0) {
                    System.err.println("Not enough product: " + cartItem.getName());
                }
            }
        }
        emailService.sendOrderEmail(request);
    }
}