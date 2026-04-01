package com.example.demoShop.service;


import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String mailTo;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendOrderEmail(OrderRequest request) {
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
