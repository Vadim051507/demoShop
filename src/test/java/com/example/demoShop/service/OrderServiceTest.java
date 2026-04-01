package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import com.example.demoShop.dto.OrderRequest;
import com.example.demoShop.repository.OrderRepository;
import com.example.demoShop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldSendEmailWhenOrderPlaced() {
        when(productRepository.decreaseStock(anyLong(), anyInt())).thenReturn(1);

        orderService.processOrder(buildTestOrder());

        verify(emailService, times(1)).sendOrderEmail(any(OrderRequest.class));
    }

    @Test
    void shouldSaveOrderToRepository() {
        when(productRepository.decreaseStock(anyLong(), anyInt())).thenReturn(1);

        orderService.processOrder(buildTestOrder());

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void shouldDecreaseStockForEachItem() {
        when(productRepository.decreaseStock(anyLong(), anyInt())).thenReturn(1);

        orderService.processOrder(buildTestOrder());

        verify(productRepository, times(2)).decreaseStock(anyLong(), anyInt());
    }

    @Test
    void shouldSendEmailWithCorrectOrder() {
        when(productRepository.decreaseStock(anyLong(), anyInt())).thenReturn(1);
        OrderRequest order = buildTestOrder();

        orderService.processOrder(order);

        verify(emailService).sendOrderEmail(order);
    }

    @Test
    void shouldNotThrowWhenStockDecreaseReturnsZero() {
        when(productRepository.decreaseStock(anyLong(), anyInt())).thenReturn(0);

        assertDoesNotThrow(() -> orderService.processOrder(buildTestOrder()));
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