package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.entity.Order;
import com.example.demoShop.entity.OrderItem;
import com.example.demoShop.repository.OrderRepository;
import com.example.demoShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final JavaMailSender mailSender;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Value("${app.mail.to}")
    private String mailTo;

    public OrderService(JavaMailSender mailSender, OrderRepository orderRepository, ProductRepository productRepository) {
        this.mailSender = mailSender;
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

        // 2. Відправити email — помилка не ламає замовлення
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject("Нове замовлення від " + request.getName());
            message.setText(buildOrderText(request));
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildOrderText(OrderRequest order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Нове замовлення\n=========================\n\n");
        sb.append("Ім'я: ").append(order.getName()).append("\n");
        sb.append("Телефон: ").append(order.getPhone()).append("\n");
        sb.append("Адреса: ").append(order.getAddress()).append("\n");
        sb.append("Доставка: ").append(order.getDeliveryType()).append("\n");

        if (order.getComment() != null && !order.getComment().isEmpty()) {
            sb.append("Коментар: ").append(order.getComment()).append("\n");
        }

        sb.append("\nТовари:\n");
        for (CartItem item : order.getItems()) {
            sb.append("  • ").append(item.getName())
                    .append(" × ").append(item.getQuantity())
                    .append(" = ").append(item.getPrice() * item.getQuantity())
                    .append(" грн\n");
        }

        sb.append("\nДо сплати: ").append(order.getTotal()).append(" грн\n");
        return sb.toString();
    }
}