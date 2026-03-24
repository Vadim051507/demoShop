package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String mailTo;

    public OrderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void processOrder(OrderRequest order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject("New order from " + order.getName());
        message.setText(buildOrderText(order));
        mailSender.send(message);
    }

    private String buildOrderText(OrderRequest order) {
        StringBuilder sb = new StringBuilder();
        sb.append("New order\n");
        sb.append("=========================\n\n");
        sb.append("Name:  ").append(order.getName()).append("\n");
        sb.append("Phone:  ").append(order.getPhone()).append("\n");
        return null;
    }
        //TODO: Продовжити написання функціоналу
}
