package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.entity.Order;
import com.example.demoShop.entity.OrderItem;
import com.example.demoShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final JavaMailSender mailSender;
    private final OrderRepository orderRepository;

    @Value("${app.mail.to}")
    private String mailTo;

    public OrderService(JavaMailSender mailSender,  OrderRepository orderRepository) {
        this.mailSender = mailSender;
        this.orderRepository = orderRepository;
    }

    public void processOrder(OrderRequest request) {
        Order order = new Order();
        order.setName(request.getName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setDeliveryType(request.getDeliveryType());
        order.setComment(request.getComment());
        order.setTotal(request.getTotal());

        for (CartItem cartItem : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(cartItem.getProductId());
            item.setName(cartItem.getName());
            item.setPrice(cartItem.getPrice());
            item.setImg(cartItem.getImg());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order);
            order.getItems().add(item);
        }

        orderRepository.save(order);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailTo);
        message.setSubject("New order from " +  order.getName());
        message.setText(buildOrderText(request));
        mailSender.send(message);
    }

    private String buildOrderText(OrderRequest order) {
        StringBuilder sb = new StringBuilder();
        sb.append("New order\n");
        sb.append("=========================\n\n");
        sb.append("Name:  ").append(order.getName()).append("\n");
        sb.append("Phone:  ").append(order.getPhone()).append("\n");
        sb.append("Address:  ").append(order.getAddress()).append("\n");
        sb.append("Delivery type:  ").append(order.getDeliveryType()).append("\n");

        if (order.getComment() != null && !order.getComment().isEmpty()){
            sb.append("Comment:  ").append(order.getComment()).append("\n");
        }

        sb.append("\nGoods:\n");
        for (CartItem item : order.getItems()) {
            sb.append("  • ").append(item.getName())
                    .append(" × ").append(item.getQuantity())
                    .append(" = ").append(item.getPrice() * item.getQuantity())
                    .append(" грн\n");
        }

        sb.append("\nTo by paid: ").append(order.getTotal()).append(" uan\n");
        return sb.toString();
    }

}
