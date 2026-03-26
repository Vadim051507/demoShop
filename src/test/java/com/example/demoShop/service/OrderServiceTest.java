package com.example.demoShop.service;


import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.List;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Mock
    private OrderRepository orderRepository;
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private OrderService orderService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendEmailWhenOrderPlaced() {
        OrderRequest order = buildTestOrder();
        orderService.processOrder(order);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldSendToCorrectRecipient() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        orderService.processOrder(buildTestOrder());

        verify(mailSender).send(captor.capture());
        SimpleMailMessage sent = captor.getValue();

        assertArrayEquals(new String[]{"test@gmail.com"}, sent.getTo());
    }

    @Test
    void shouldIncludeClientNameInSubject() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        orderService.processOrder(buildTestOrder());

        verify(mailSender).send(captor.capture());
        assertTrue(captor.getValue().getSubject().contains("Vadim"));
    }

    @Test
    void shouldIncludeAllItemsInEmailBody() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        orderService.processOrder(buildTestOrder());

        verify(mailSender).send(captor.capture());
        String body = captor.getValue().getText();

        assertTrue(body.contains("Roses"));
        assertTrue(body.contains("Tulips"));
        assertTrue(body.contains("1850"));
    }

    @Test
    void shouldNotSendEmailIfMailSenderThrows() {
        doThrow(new RuntimeException("SMTP error"))
                .when(mailSender).send(any(SimpleMailMessage.class));

        assertThrows(RuntimeException.class,
                () -> orderService.processOrder((buildTestOrder())));
    }

    private OrderRequest buildTestOrder() {
        OrderRequest order = new OrderRequest();
        order.setName("Vadim");
        order.setPhone("+380966008376");
        order.setAddress("str. Kvitkova, 12");
        order.setDeliveryType("Fast");
        order.setComment("No comment");

        CartItem roses = new CartItem(1L, "Roses", 1100, "img1.jpg");
        CartItem tulips = new CartItem(2L, "Tulips", 750, "img2.jpg");
        order.setItems(List.of(roses, tulips));
        order.setTotal(1850);
        return order;
    }

}
